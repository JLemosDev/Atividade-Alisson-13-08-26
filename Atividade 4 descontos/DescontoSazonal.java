public class DescontoSazonal implements EstrategiaDesconto {
    @Override
    public double calcular(Pedido pedido) {
        return 50.0; // Desconto fixo de R$ 50
    }
}