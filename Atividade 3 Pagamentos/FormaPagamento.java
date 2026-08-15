import java.math.BigDecimal;

public abstract class FormaPagamento {
    protected final BigDecimal valor;

    protected FormaPagamento(BigDecimal valor) {
        this.valor = valor;
    }

    public abstract BigDecimal calcularTaxa();

    public BigDecimal valorFinal() {
        return valor.add(calcularTaxa());
    }
}
