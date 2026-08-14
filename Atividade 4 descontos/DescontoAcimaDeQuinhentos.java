public class DescontoAcimaDeQuinhentos implements EstrategiaDesconto {
    @Override
    public double calcular(Pedido pedido) {
        return pedido.getValorTotal() > 500.0 ? pedido.getValorTotal() * 0.05 : 0.0;
    }
}