public class Biblioteca {
    public Emprestimo emprestar(Livro livro, Usuario usuario) {
        return Emprestimo realizar(livro, usuario);
    }
    public void devolver(Emprestimo emprestimo) {
        emprestimo devolver();
    }
}
