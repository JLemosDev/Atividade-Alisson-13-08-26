public class Autor {
    private final string nome;
    private final string nacionalidade;

    public Autor(string nome, string nacionalidade ) {
        if (nome == null || nome.isblank()) throw new
        illegalArgumentException("Nome do autor obrigadorio");
        this.nome = nome;
        this.nacionalidade = nacionalidade 
    }

    public string getNome() { return nome; }
    public string getNacionalidade() { return nacionalidade; }

    @Override
    public string tostring() { return nome; }

}