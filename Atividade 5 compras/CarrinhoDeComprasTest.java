package com.loja.carrinho;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarrinhoDeComprasTest {

    private Produto notebook;
    private Produto mouse;
    private CarrinhoDeCompras carrinho;

    @BeforeEach
    void setUp() {
        notebook = new Produto("P1", "Notebook", 3000.0);
        mouse = new Produto("P2", "Mouse", 100.0);
        carrinho = new CarrinhoDeCompras();
    }

    @Test
    void testCalculoSubtotalETotalComFreteEDesconto() {
        carrinho.adicionarProduto(notebook, 1); 
        carrinho.adicionarProduto(mouse, 2);    

        assertEquals(3200.0, carrinho.calcularSubtotal(), 0.001);

        carrinho.aplicarDesconto(200.0);
        carrinho.definirFrete(50.0);

        assertEquals(3050.0, carrinho.calcularTotal(), 0.001);
    }

    @Test
    void testProtecaoDeColecaoInterna() {
        carrinho.adicionarProduto(mouse, 1);

        assertThrows(UnsupportedOperationException.class, () -> {
            carrinho.getItens().clear();
        });
    }

    @Test
    void testAdicionarMesmoProdutoAtualizaQuantidade() {
        carrinho.adicionarProduto(mouse, 1);
        carrinho.adicionarProduto(mouse, 3);

        assertEquals(1, carrinho.getItens().size());
        assertEquals(4, carrinho.getItens().get(0).getQuantidade());
        assertEquals(400.0, carrinho.calcularSubtotal(), 0.001);
    }
}