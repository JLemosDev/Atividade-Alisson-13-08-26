@Tabela(nome = "clientes")
public class Cliente {

    @Coluna(nome = "id")
    private int id;

    @Coluna(nome = "nome")
    private String nome;

    @Coluna(nome = "email")
    private String email;

    private String campoIgnorado;

    public Cliente(int id, String nome, String email, String campoIgnorado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.campoIgnorado = campoIgnorado;
    }
}
