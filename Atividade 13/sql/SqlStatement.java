package orm.sql;

import java.util.List;

public record SqlStatement(String sql, List<Object> params) {
    @Override
    public String toString() {
        return sql + "  -- params=" + params;
    }
}
