import java.time.LocalDate;

public class Emprestimo {
    private final Livro livro;
    private final Usuario usuario;
    private final LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    private Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
    }

    static Emprestimo realizar(Livro livro, Usuario usuario) {
        if (!usuario.podePegarEmprestado()) {
            throw new IllegalStateException("Usuário atingiu o limite de empréstimos ativos");
        }
        livro.marcarComoEmprestado();
        Emprestimo emprestimo = new Emprestimo(livro, usuario, LocalDate.now());
        usuario.registrarEmprestimo(emprestimo);
        return emprestimo;
    }

    void devolver() {
        if (dataDevolucao != null) throw new IllegalStateException("Empréstimo já devolvido");
        livro.marcarComoDevolvido();
        usuario.registrarDevolucao(this);
        this.dataDevolucao = LocalDate.now();
    }

    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }

    @Override
    public String toString() {
        return usuario.getNome() + " -> " + livro.getTitulo() +
               (dataDevolucao == null ? " (em aberto)" : " (devolvido em " + dataDevolucao + ")");
    }
}