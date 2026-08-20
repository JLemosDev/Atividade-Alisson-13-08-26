package orm.persistence;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simula o "motor" de um banco de dados. Numa aplicacao real, esta classe
 * seria substituida por uma conexao JDBC (java.sql.Connection) que de fato
 * enviaria os comandos SQL gerados pelo SqlBuilder para um banco relacional.
 *
 * Aqui guardamos as linhas como Map<coluna, valor> para simular o formato
 * "tabular" que um banco real usaria -- nunca guardamos o objeto de entidade
 * diretamente, para deixar claro que a camada de persistencia nao conhece
 * o modelo de objetos, apenas linhas e colunas.
 */
public class InMemoryDatabase {

    private final Map<String, Map<Object, Map<String, Object>>> tables = new ConcurrentHashMap<>();

    public void insert(String table, Object id, Map<String, Object> row) {
        tables.computeIfAbsent(table, t -> new LinkedHashMap<>()).put(id, row);
    }

    public Map<String, Object> findById(String table, Object id) {
        Map<Object, Map<String, Object>> rows = tables.get(table);
        if (rows == null) return null;
        return rows.get(id);
    }

    public void update(String table, Object id, Map<String, Object> row) {
        Map<Object, Map<String, Object>> rows = tables.computeIfAbsent(table, t -> new LinkedHashMap<>());
        rows.put(id, row);
    }

    public boolean delete(String table, Object id) {
        Map<Object, Map<String, Object>> rows = tables.get(table);
        if (rows == null) return false;
        return rows.remove(id) != null;
    }
}
