package com.loja.repositorio;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioTest {

    @Test
    void testOperacoesCrudClienteSemCasts() {
        Repositorio<Cliente, String> repoCliente = new Repositorio<>();
        Cliente c1 = new Cliente("C1", "Maria");

        repoCliente.salvar(c1);

        Cliente recuperado = repoCliente.buscarPorId("C1").orElse(null);
        assertNotNull(recuperado);
        assertEquals("Maria", recuperado.getNome());

        assertEquals(1, repoCliente.listarTodos().size());
        assertTrue(repoCliente.remover("C1"));
        assertEquals(0, repoCliente.listarTodos().size());
    }

    @Test
    void testMetodosGenericosIndependentes() {
        Repositorio<Produto, String> repoProduto = new Repositorio<>();
        repoProduto.salvar(new Produto("P1", "Teclado", 150.0));
        repoProduto.salvar(new Produto("P2", "Monitor", 900.0));

        List<Produto> produtos = repoProduto.listarTodos();

        List<Produto> caros = RepositorioUtils.filtrar(produtos, p -> p.getPreco() > 500.0);
        assertEquals(1, caros.size());
        assertEquals("Monitor", caros.get(0).getNome());

        List<String> nomes = RepositorioUtils.mapear(produtos, Produto::getNome);
        assertTrue(nomes.contains("Teclado"));
        assertTrue(nomes.contains("Monitor"));
    }
}