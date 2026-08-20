package orm.sql;

import orm.metadata.ColumnMetadata;
import orm.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsavel exclusivamente por gerar comandos SQL (texto) a partir dos
 * metadados de uma entidade. Nao sabe nada sobre Reflection nem sobre
 * como os dados sao efetivamente persistidos.
 */
public final class SqlBuilder {

    private SqlBuilder() {}

    public static <T> SqlStatement buildInsert(EntityMetadata<T> metadata, T entity) {
        List<ColumnMetadata> columns = metadata.getColumns();
        String columnNames = columns.stream()
                .map(ColumnMetadata::getColumnName)
                .collect(Collectors.joining(", "));
        String placeholders = columns.stream().map(c -> "?").collect(Collectors.joining(", "));

        List<Object> params = new ArrayList<>();
        for (ColumnMetadata column : columns) {
            params.add(column.getValue(entity));
        }

        String sql = "INSERT INTO %s (%s) VALUES (%s)"
                .formatted(metadata.getTableName(), columnNames, placeholders);
        return new SqlStatement(sql, params);
    }

    public static <T> SqlStatement buildSelectById(EntityMetadata<T> metadata, Object id) {
        String sql = "SELECT * FROM %s WHERE %s = ?"
                .formatted(metadata.getTableName(), metadata.getIdColumn().getColumnName());
        return new SqlStatement(sql, List.of(id));
    }

    public static <T> SqlStatement buildUpdate(EntityMetadata<T> metadata, T entity) {
        String setClause = metadata.getNonIdColumns().stream()
                .map(c -> c.getColumnName() + " = ?")
                .collect(Collectors.joining(", "));

        List<Object> params = new ArrayList<>();
        for (ColumnMetadata column : metadata.getNonIdColumns()) {
            params.add(column.getValue(entity));
        }
        params.add(metadata.getIdColumn().getValue(entity));

        String sql = "UPDATE %s SET %s WHERE %s = ?"
                .formatted(metadata.getTableName(), setClause, metadata.getIdColumn().getColumnName());
        return new SqlStatement(sql, params);
    }

    public static <T> SqlStatement buildDeleteById(EntityMetadata<T> metadata, Object id) {
        String sql = "DELETE FROM %s WHERE %s = ?"
                .formatted(metadata.getTableName(), metadata.getIdColumn().getColumnName());
        return new SqlStatement(sql, List.of(id));
    }
}
