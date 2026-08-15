package com.loja.desconto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraDeDescontosTest {

    @Test
    void testClienteComumSemCampanha() {
        Pedido pedido = new Pedido(100.0);
        CalculadoraDeDescontos calculadora = new CalculadoraDeDescontos();
        calculadora.adicionarEstrategia(new DescontoClienteComum());

        assertEquals(100.0, calculadora.calcularTotalComDesconto(pedido), 0.001);
    }

    @Test
    void testClientePremiumComDescontoSazonal() {
        Pedido pedido = new Pedido(200.0);
        CalculadoraDeDescontos calculadora = new CalculadoraDeDescontos();

        calculadora.adicionarEstrategia(new DescontoClientePremium());
        calculadora.adicionarEstrategia(new DescontoSazonal());

        assertEquals(130.0, calculadora.calcularTotalComDesconto(pedido), 0.001);
    }

    @Test
    void testClienteCorporativoGrandeCompraMultiplasCampanhas() {
        Pedido pedido = new Pedido(1500.0);
        CalculadoraDeDescontos calculadora = new CalculadoraDeDescontos();

        calculadora.adicionarEstrategia(new DescontoClienteCorporativo());
        calculadora.adicionarEstrategia(new DescontoAcimaDeQuinhentos());
        calculadora.adicionarEstrategia(new DescontoSazonal());

        assertEquals(1150.0, calculadora.calcularTotalComDesconto(pedido), 0.001);
    }
}