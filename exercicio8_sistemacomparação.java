import java.util.*;

class Funcionario implements Comparable<Funcionario> {

    private String nome;
    private double salario;
    private String setor;

    public Funcionario(String nome, double salario, String setor) {
        this.nome = nome;
        this.salario = salario;
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public double getSalario() {
        return salario;
    }

    public String getSetor() {
        return setor;
    }

    // Ordenação natural por nome
    @Override
    public int compareTo(Funcionario outro) {
        return this.nome.compareToIgnoreCase(outro.nome);
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                " | Salário: R$ " + salario +
                " | Setor: " + setor;
    }
}

public class SistemaComparacao {

    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario("Carlos", 3500, "TI"));
        funcionarios.add(new Funcionario("Ana", 5000, "RH"));
        funcionarios.add(new Funcionario("Pedro", 2800, "Financeiro"));
        funcionarios.add(new Funcionario("Mariana", 4500, "TI"));

        // Ordenação natural: nome
        Collections.sort(funcionarios);

        System.out.println("=== ORDEM POR NOME ===");
        funcionarios.forEach(System.out::println);

        // Salário crescente
        Comparator<Funcionario> salarioCrescente =
                Comparator.comparingDouble(Funcionario::getSalario);

        funcionarios.sort(salarioCrescente);

        System.out.println("\n=== SALÁRIO CRESCENTE ===");
        funcionarios.forEach(System.out::println);

        // Salário decrescente
        Comparator<Funcionario> salarioDecrescente =
                Comparator.comparingDouble(Funcionario::getSalario)
                        .reversed();

        funcionarios.sort(salarioDecrescente);

        System.out.println("\n=== SALÁRIO DECRESCENTE ===");
        funcionarios.forEach(System.out::println);

        // Ordenação por setor
        Comparator<Funcionario> setor =
                Comparator.comparing(Funcionario::getSetor)
                        .thenComparing(Funcionario::getNome);

        funcionarios.sort(setor);

        System.out.println("\n=== ORDEM POR SETOR ===");
        funcionarios.forEach(System.out::println);
    }
}
