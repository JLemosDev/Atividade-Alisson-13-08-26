package orm.metadata;

import java.util.List;

public class EntityMetadata<T> {
    private final Class<T> entityClass;
    private final String tableName;
    private final ColumnMetadata idColumn;
    private final List<ColumnMetadata> columns; // inclui a coluna de id

    public EntityMetadata(Class<T> entityClass, String tableName,
                           ColumnMetadata idColumn, List<ColumnMetadata> columns) {
        this.entityClass = entityClass;
        this.tableName = tableName;
        this.idColumn = idColumn;
        this.columns = columns;
    }

    public Class<T> getEntityClass() { return entityClass; }
    public String getTableName() { return tableName; }
    public ColumnMetadata getIdColumn() { return idColumn; }
    public List<ColumnMetadata> getColumns() { return columns; }

    public List<ColumnMetadata> getNonIdColumns() {
        return columns.stream().filter(c -> !c.isId()).toList();
    }
}
