import java.math.BigDecimal;

public class CartaoCredito extends FormaPagamento implements Processavel, Estornavel {
    private final String numeroMascarado;
    private final int parcelas;

    public CartaoCredito(BigDecimal valor, String numeroMascarado, int parcelas) {
        super(valor);
        this.numeroMascarado = numeroMascarado;
        this.parcelas = parcelas;
    }

    @Override
    public BigDecimal calcularTaxa() {
        return valor.multiply(new BigDecimal("0.035")); // 3,5% cartão
    }

    @Override
    public boolean validar() {
        return numeroMascarado != null && parcelas > 0 && parcelas <= 12;
    }

    @Override
    public void processar() {
        System.out.println("Processando cartão " + numeroMascarado + " em " + parcelas + "x, total R$ " + valorFinal());
    }

    @Override
    public void estornar() {
        System.out.println("Estornando cartão " + numeroMascarado);
    }
}
