import java.util.ArrayList;
import java.util.List;

public class CalculadoraDeDescontos {
    private final List<EstrategiaDesconto> estrategias = new ArrayList<>();

    public void adicionarEstrategia(EstrategiaDesconto estrategia) {
        this.estrategias.add(estrategia);
    }

    public double calcularTotalComDesconto(Pedido pedido) {
        double descontoTotal = estrategias.stream()
                .mapToDouble(estrategia -> estrategia.calcular(pedido))
                .sum();

        double descontoFinal = Math.min(descontoTotal, pedido.getValorTotal());
        return pedido.getValorTotal() - descontoFinal;
    }
}