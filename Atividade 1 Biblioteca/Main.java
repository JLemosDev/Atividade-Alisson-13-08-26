public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        Autor tolkien = new Autor("J.R.R. Tolkien", "Britânico");
        Livro senhorDosAneis = new Livro("978-0-618", "O Senhor dos Anéis", tolkien);
        Livro hobbit = new Livro("978-0-547", "O Hobbit", tolkien);

        Usuario joao = new Usuario("2026001", "João Vitor");

        Emprestimo e1 = biblioteca.emprestar(senhorDosAneis, joao);
        System.out.println("Empréstimo realizado: " + e1);

        try {
            biblioteca.emprestar(senhorDosAneis, joao);
        } catch (IllegalStateException ex) {
            System.out.println("Erro esperado: " + ex.getMessage());
        }

        biblioteca.devolver(e1);
        System.out.println("Após devolução: " + senhorDosAneis);

        Emprestimo e2 = biblioteca.emprestar(hobbit, joao);
        System.out.println("Novo empréstimo: " + e2);
    }
}
