import java.math.BigDecimal;

public class Gerente extends Funcionario {
    private static final BigDecimal BONUS_GESTAO = new BigDecimal("2000.00");
    private final int tamanhoEquipe;

    public Gerente(String nome, BigDecimal salarioBase, int tamanhoEquipe) {
        super(nome, salarioBase);
        this.tamanhoEquipe = tamanhoEquipe;
    }

    @Override
    public BigDecimal calcularRemuneracao() {
        BigDecimal bonusPorEquipe = new BigDecimal(tamanhoEquipe).multiply(new BigDecimal("150.00"));
        return salarioBase.add(BONUS_GESTAO).add(bonusPorEquipe);
    }
}
