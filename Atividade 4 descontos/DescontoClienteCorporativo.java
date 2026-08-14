public class DescontoClienteCorporativo implements EstrategiaDesconto {
    @Override
    public double calcular(Pedido pedido) {
        if (pedido.getValorTotal() > 1000.0) {
            return pedido.getValorTotal() * 0.15;
        }
        return pedido.getValorTotal() * 0.05;
    }
}