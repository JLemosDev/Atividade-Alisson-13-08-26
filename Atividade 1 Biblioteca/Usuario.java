import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private static final int LIMITE_EMPRESTIMOS_ATIVOS = 3;

    private final String matricula;
    private final String nome;
    private final List<Emprestimo> emprestimosAtivos = new ArrayList<>();

    public Usuario(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }


    boolean podePegarEmprestado() {
        return emprestimosAtivos.size() < LIMITE_EMPRESTIMOS_ATIVOS;
    }

    void registrarEmprestimo(Emprestimo emprestimo) {
        if (!podePegarEmprestado()) {
            throw new IllegalStateException(nome + " já possui " + LIMITE_EMPRESTIMOS_ATIVOS + " empréstimos ativos");
        }
        emprestimosAtivos.add(emprestimo);
    }

    void registrarDevolucao(Emprestimo emprestimo) {
        emprestimosAtivos.remove(emprestimo);
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public List<Emprestimo> getEmprestimosAtivos() { return Collections.unmodifiableList(emprestimosAtivos); }
}
