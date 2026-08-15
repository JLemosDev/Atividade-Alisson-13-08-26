import java.math.BigDecimal;

public class Desenvolvedor extends Funcionario {
    private final int horasExtras;
    private static final BigDecimal VALOR_HORA_EXTRA = new BigDecimal("45.00");

    public Desenvolvedor(String nome, BigDecimal salarioBase, int horasExtras) {
        super(nome, salarioBase);
        this.horasExtras = horasExtras;
    }

    @Override
    public BigDecimal calcularRemuneracao() {
        return salarioBase.add(VALOR_HORA_EXTRA.multiply(new BigDecimal(horasExtras)));
    }
}
