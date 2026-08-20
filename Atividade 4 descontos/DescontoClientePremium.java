public class DescontoClientePremium implements EstrategiaDesconto {
    @Override
    public double calcular(Pedido pedido) {
        return pedido.getValorTotal() * 0.10;
    }
}
