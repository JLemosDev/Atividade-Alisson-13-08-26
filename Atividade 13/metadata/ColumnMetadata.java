package orm.metadata;

import java.lang.reflect.Field;

public class ColumnMetadata {
    private final Field field;
    private final String columnName;
    private final boolean id;

    public ColumnMetadata(Field field, String columnName, boolean id) {
        this.field = field;
        this.columnName = columnName;
        this.id = id;
        this.field.setAccessible(true);
    }

    public Field getField() { return field; }
    public String getColumnName() { return columnName; }
    public boolean isId() { return id; }

    public Object getValue(Object entityInstance) {
        try {
            return field.get(entityInstance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Nao foi possivel ler o campo " + field.getName(), e);
        }
    }

    public void setValue(Object entityInstance, Object value) {
        try {
            field.set(entityInstance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Nao foi possivel escrever no campo " + field.getName(), e);
        }
    }
}
