package com.loja.carrinho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CarrinhoDeCompras {
    private final List<ItemCarrinho> itens = new ArrayList<>();
    private double valorFrete = 0.0;
    private double valorDesconto = 0.0;


    public void adicionarProduto(Produto produto, int quantidade) {
        Optional<ItemCarrinho> itemExistente = itens.stream()
                .filter(item -> item.getProduto().equals(produto))
                .findFirst();

        if (itemExistente.isPresent()) {
            int novaQuantidade = itemExistente.get().getQuantidade() + quantidade;
            itens.remove(itemExistente.get());
            itens.add(new ItemCarrinho(produto, novaQuantidade));
        } else {
            itens.add(new ItemCarrinho(produto, quantidade));
        }
    }

    public void removerProduto(String produtoId) {
        itens.removeIf(item -> item.getProduto().getId().equals(produtoId));
    }

    public List<ItemCarrinho> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void aplicarDesconto(double valorDesconto) {
        if (valorDesconto < 0) {
            throw new IllegalArgumentException("O desconto não pode ser negativo.");
        }
        this.valorDesconto = valorDesconto;
    }

    public void definirFrete(double valorFrete) {
        if (valorFrete < 0) {
            throw new IllegalArgumentException("O frete não pode ser negativo.");
        }
        this.valorFrete = valorFrete;
    }

    public double calcularSubtotal() {
        return itens.stream()
                .mapToDouble(ItemCarrinho::getSubtotal)
                .sum();
    }

    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        double subtotalComDesconto = Math.max(0.0, subtotal - valorDesconto);
        return subtotalComDesconto + valorFrete;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }
}