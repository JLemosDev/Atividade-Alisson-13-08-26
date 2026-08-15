import java.math.BigDecimal;
import java.util.List;

public class FolhaDePagamento {
    public BigDecimal processar(List<Funcionario> funcionarios) {
        BigDecimal total = BigDecimal.ZERO;
        for (Funcionario f : funcionarios) {
            System.out.println(f);
            total = total.add(f.calcularRemuneracao()); // polimorfismo: nenhum if/else por tipo
        }
        return total;
    }
}
