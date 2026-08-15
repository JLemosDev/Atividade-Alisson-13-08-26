import java.math.BigDecimal;

public class Pix extends FormaPagamento implements Processavel {
    private final String chave;

    public Pix(BigDecimal valor, String chave) {
        super(valor);
        this.chave = chave;
    }

    @Override
    public BigDecimal calcularTaxa() {
        return BigDecimal.ZERO; // Pix sem taxa
    }

    @Override
    public boolean validar() {
        return chave != null && !chave.isBlank();
    }

    @Override
    public void processar() {
        System.out.println("Processando Pix para chave " + chave + ", total R$ " + valorFinal());
    }
}
