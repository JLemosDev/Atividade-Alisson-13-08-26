import java.math.BigDecimal;


public abstract class Funcionario {
    protected final String nome;
    protected final BigDecimal salarioBase;

    protected Funcionario(String nome, BigDecimal salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    public abstract BigDecimal calcularRemuneracao();

    public String getNome() { return nome; }

    @Override
    public String toString() {
        return String.format("%-12s %-15s R$ %.2f", getClass().getSimpleName(), nome, calcularRemuneracao());
    }
}
