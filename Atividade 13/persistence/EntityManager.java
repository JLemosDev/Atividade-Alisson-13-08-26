package orm.persistence;

import orm.metadata.ColumnMetadata;
import orm.metadata.EntityMetadata;
import orm.metadata.MetadataScanner;
import orm.sql.SqlBuilder;
import orm.sql.SqlStatement;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * EntityManager<T> generico: e o ponto de entrada usado pelo cliente do
 * "mini ORM". Ele orquestra as outras tres camadas:
 *   - orm.metadata  -> descobre a estrutura da entidade via Reflection
 *   - orm.sql       -> gera o texto SQL correspondente a operacao
 *   - orm.persistence.InMemoryDatabase -> "executa" o comando
 *
 * O uso de Generics (T) garante seguranca de tipos: um EntityManager<User>
 * so aceita e retorna instancias de User, verificado em tempo de compilacao.
 */
public class EntityManager<T> {

    private final Class<T> entityClass;
    private final EntityMetadata<T> metadata;
    private final InMemoryDatabase database;
    private final boolean verbose;

    public EntityManager(Class<T> entityClass, InMemoryDatabase database) {
        this(entityClass, database, true);
    }

    public EntityManager(Class<T> entityClass, InMemoryDatabase database, boolean verbose) {
        this.entityClass = entityClass;
        this.database = database;
        this.verbose = verbose;
        // reflection acontece uma unica vez aqui (e fica em cache no scanner)
        this.metadata = MetadataScanner.scan(entityClass);
    }

    public void save(T entity) {
        Object id = metadata.getIdColumn().getValue(entity);
        boolean exists = id != null && database.findById(metadata.getTableName(), id) != null;

        SqlStatement statement = exists
                ? SqlBuilder.buildUpdate(metadata, entity)
                : SqlBuilder.buildInsert(metadata, entity);
        log(statement);

        Map<String, Object> row = toRow(entity);
        if (exists) {
            database.update(metadata.getTableName(), id, row);
        } else {
            database.insert(metadata.getTableName(), id, row);
        }
    }

    public T findById(Object id) {
        SqlStatement statement = SqlBuilder.buildSelectById(metadata, id);
        log(statement);

        Map<String, Object> row = database.findById(metadata.getTableName(), id);
        if (row == null) {
            return null;
        }
        return fromRow(row);
    }

    public boolean delete(Object id) {
        SqlStatement statement = SqlBuilder.buildDeleteById(metadata, id);
        log(statement);
        return database.delete(metadata.getTableName(), id);
    }

    private Map<String, Object> toRow(T entity) {
        Map<String, Object> row = new LinkedHashMap<>();
        for (ColumnMetadata column : metadata.getColumns()) {
            row.put(column.getColumnName(), column.getValue(entity));
        }
        return row;
    }

    private T fromRow(Map<String, Object> row) {
        try {
            T instance = entityClass.getDeclaredConstructor().newInstance();
            for (ColumnMetadata column : metadata.getColumns()) {
                column.setValue(instance, row.get(column.getColumnName()));
            }
            return instance;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(
                entityClass.getName() + " precisa de um construtor sem argumentos", e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Falha ao instanciar " + entityClass.getName(), e);
        }
    }

    private void log(SqlStatement statement) {
        if (verbose) {
            System.out.println("[SQL] " + statement);
        }
    }
}
