import java.math.BigDecimal;
import java.time.LocalDate;

public class Boleto extends FormaPagamento implements Processavel {
    private final LocalDate vencimento;

    public Boleto(BigDecimal valor, LocalDate vencimento) {
        super(valor);
        this.vencimento = vencimento;
    }

    @Override
    public BigDecimal calcularTaxa() {
        return new BigDecimal("2.50"); // taxa fixa de emissão
    }

    @Override
    public boolean validar() {
        return vencimento != null && !vencimento.isBefore(LocalDate.now());
    }

    @Override
    public void processar() {
        System.out.println("Boleto gerado, vencimento " + vencimento + ", total R$ " + valorFinal());
    }
}
