package orm.metadata;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Responsavel exclusivamente por ler anotacoes via Reflection e montar
 * o EntityMetadata correspondente. Nao conhece SQL nem persistencia.
 */
public final class MetadataScanner {

    // cache simples para nao repetir reflection a cada chamada
    private static final Map<Class<?>, EntityMetadata<?>> CACHE = new ConcurrentHashMap<>();

    private MetadataScanner() {}

    @SuppressWarnings("unchecked")
    public static <T> EntityMetadata<T> scan(Class<T> entityClass) {
        return (EntityMetadata<T>) CACHE.computeIfAbsent(entityClass, MetadataScanner::doScan);
    }

    private static <T> EntityMetadata<T> doScan(Class<T> entityClass) {
        Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
        if (entityAnnotation == null) {
            throw new IllegalArgumentException(
                "A classe " + entityClass.getName() + " nao esta anotada com @Entity");
        }

        String tableName = entityAnnotation.table().isBlank()
                ? entityClass.getSimpleName().toLowerCase()
                : entityAnnotation.table();

        List<ColumnMetadata> columns = new ArrayList<>();
        ColumnMetadata idColumn = null;

        for (Field field : entityClass.getDeclaredFields()) {
            boolean isId = field.isAnnotationPresent(Id.class);
            Column columnAnnotation = field.getAnnotation(Column.class);

            if (!isId && columnAnnotation == null) {
                // campo sem anotacao relevante e ignorado pelo ORM
                continue;
            }

            String columnName = (columnAnnotation != null && !columnAnnotation.name().isBlank())
                    ? columnAnnotation.name()
                    : field.getName();

            ColumnMetadata column = new ColumnMetadata(field, columnName, isId);
            columns.add(column);

            if (isId) {
                if (idColumn != null) {
                    throw new IllegalStateException(
                        "Entidade " + entityClass.getName() + " possui mais de um campo @Id");
                }
                idColumn = column;
            }
        }

        if (idColumn == null) {
            throw new IllegalStateException(
                "Entidade " + entityClass.getName() + " precisa de um campo anotado com @Id");
        }

        return new EntityMetadata<>(entityClass, tableName, idColumn,
                Collections.unmodifiableList(columns));
    }
}
