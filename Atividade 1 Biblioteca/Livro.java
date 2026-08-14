public class Livro {
    private final String isbn;
    private final String titulo;
    private final Autor autor;
    private boolean disponivel;

    public Livro(String isbn, String titulo, Autor autor) {
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN obrigatório");
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("Título obrigatório");
        if (autor == null) throw new IllegalArgumentException("Autor obrigatório");
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }


    void marcarComoEmprestado() {
        if (!disponivel) throw new IllegalStateException("Livro indisponível para empréstimo: " + titulo);
        this.disponivel = false;
    }

    void marcarComoDevolvido() {
        this.disponivel = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public Autor getAutor() { return autor; }
    public boolean isDisponivel() { return disponivel; }

    @Override
    public String toString() {
        return titulo + " (" + autor.getNome() + ")" + (disponivel ? " [disponível]" : " [emprestado]");
    }
}