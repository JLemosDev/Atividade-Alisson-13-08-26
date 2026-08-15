package com.loja.repositorio;

public class Produto implements Entidade<String> {
    private final String id;
    private final String nome;
    private final double preco;

    public Produto(String id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}