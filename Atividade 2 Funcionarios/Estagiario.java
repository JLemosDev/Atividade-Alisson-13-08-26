import java.math.BigDecimal;

public class Estagiario extends Funcionario {
    private static final BigDecimal AUXILIO_TRANSPORTE = new BigDecimal("220.00");

    public Estagiario(String nome, BigDecimal bolsa) {
        super(nome, bolsa);
    }

    @Override
    public BigDecimal calcularRemuneracao() {
        return salarioBase.add(AUXILIO_TRANSPORTE);
    }
}
