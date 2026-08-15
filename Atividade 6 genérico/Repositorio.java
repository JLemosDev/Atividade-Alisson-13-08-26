package com.loja.repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Repositorio<T extends Entidade<ID>, ID> {
    private final Map<ID, T> armazenamento = new HashMap<>();

    public void salvar(T entidade) {
        armazenamento.put(entidade.getId(), entidade);
    }

    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(armazenamento.get(id));
    }

    public List<T> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(armazenamento.values()));
    }

    public boolean remover(ID id) {
        return armazenamento.remove(id) != null;
    }
}