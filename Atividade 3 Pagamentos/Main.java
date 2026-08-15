import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pagamento processador = new Pagamento();

        List<Processavel> pagamentos = List.of(
            new CartaoCredito(new BigDecimal("500.00"), "**** 1234", 3),
            new Pix(new BigDecimal("150.00"), "joao@email.com"),
            new Boleto(new BigDecimal("300.00"), LocalDate.now().plusDays(5))
        );

        for (Processavel p : pagamentos) {
            processador.executar(p); // referência polimórfica: mesmo método para todas as formas
        }
    }
}
