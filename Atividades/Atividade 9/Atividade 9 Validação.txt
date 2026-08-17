import java.util.*;
import java.util.function.Predicate;


@FunctionalInterface
interface Regra<T> {

    String validar(T objeto);
}


class Validador<T> {

    private final List<Regra<T>> regras = new ArrayList<>();

    public Validador<T> adicionarRegra(Regra<T> regra) {
        regras.add(regra);
        return this;
    }

    public List<String> validar(T objeto) {

        List<String> erros = new ArrayList<>();

        for (Regra<T> regra : regras) {

            String erro = regra.validar(objeto);

            if (erro != null && !erro.isBlank()) {
                erros.add(erro);
            }
        }

        return erros;
    }
}


class Cliente {

    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}


class Produto {

    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}

public class FrameworkValidacao {

    public static void main(String[] args) {

        
        Validador<Cliente> validadorCliente = new Validador<>();

        validadorCliente
                .adicionarRegra(cliente ->
                        cliente.getNome() == null ||
                        cliente.getNome().isBlank()
                                ? "Nome do cliente é obrigatório."
                                : null)

                .adicionarRegra(cliente ->
                        cliente.getEmail() == null ||
                        !cliente.getEmail().contains("@")
                                ? "E-mail inválido."
                                : null);

        Cliente cliente = new Cliente("", "email-invalido");

        List<String> errosCliente =
                validadorCliente.validar(cliente);

        System.out.println("=== ERROS DO CLIENTE ===");

        if (errosCliente.isEmpty()) {
            System.out.println("Cliente válido.");
        } else {
            errosCliente.forEach(System.out::println);
        }

        
        Validador<Produto> validadorProduto = new Validador<>();

        validadorProduto
                .adicionarRegra(produto ->
                        produto.getNome() == null ||
                        produto.getNome().isBlank()
                                ? "Nome do produto é obrigatório."
                                : null)

                .adicionarRegra(produto ->
                        produto.getPreco() <= 0
                                ? "O preço deve ser maior que zero."
                                : null);

        Produto produto = new Produto("", -10);

        List<String> errosProduto =
                validadorProduto.validar(produto);

        System.out.println("\n=== ERROS DO PRODUTO ===");

        if (errosProduto.isEmpty()) {
            System.out.println("Produto válido.");
        } else {
            errosProduto.forEach(System.out::println);
        }
    }
}
