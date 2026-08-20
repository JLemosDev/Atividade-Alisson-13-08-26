public class DescontoClienteComum implements EstrategiaDesconto {
    @Override
    public double calcular(Pedido pedido) {
        return 0.0;
    }
}
