package com.loja.carrinho;

import java.util.Objects;

public final class Produto {
    private final String id;
    private final String nome;
    private final double precoUnitario;

    public Produto(String id, String nome, double precoUnitario) {
        if (precoUnitario < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        this.precoUnitario = precoUnitario;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}