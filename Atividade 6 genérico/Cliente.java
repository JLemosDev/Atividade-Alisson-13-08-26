package com.loja.repositorio;

public class Cliente implements Entidade<String> {
    private final String id;
    private final String nome;

    public Cliente(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}