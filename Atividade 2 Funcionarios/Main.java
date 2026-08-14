import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = List.of(
            new Gerente("Ana Paula", new BigDecimal("8000.00"), 6),
            new Desenvolvedor("Carlos Silva", new BigDecimal("6000.00"), 12),
            new Estagiario("Beatriz Souza", new BigDecimal("1200.00"))
        );

        FolhaDePagamento folha = new FolhaDePagamento();
        BigDecimal total = folha.processar(funcionarios);
        System.out.println("Total da folha: R$ " + total);
    }
}
