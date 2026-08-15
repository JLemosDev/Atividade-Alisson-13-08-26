import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class InspecaoClasses {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== INSPEÇÃO DE CLASSE COM REFLECTION ===");
        System.out.print("Digite o nome completo da classe: ");

        String nomeClasse = scanner.nextLine();

        try {
            // Carrega a classe em tempo de execução
            Class<?> classe = Class.forName(nomeClasse);

            System.out.println("\n=== INFORMAÇÕES DA CLASSE ===");
            System.out.println("Nome completo: " + classe.getName());
            System.out.println("Nome simples: " + classe.getSimpleName());
            System.out.println("Modificadores: "
                    + Modifier.toString(classe.getModifiers()));

            // =========================================================
            // CONSTRUTORES
            // =========================================================

            System.out.println("\n=== CONSTRUTORES ===");

            Constructor<?>[] construtores = classe.getDeclaredConstructors();

            if (construtores.length == 0) {
                System.out.println("Nenhum construtor encontrado.");
            } else {
                for (Constructor<?> construtor : construtores) {

                    System.out.println(
                            "Construtor: "
                                    + Modifier.toString(construtor.getModifiers())
                                    + " "
                                    + construtor.getName()
                                    + "("
                                    + formatarParametros(construtor.getParameterTypes())
                                    + ")"
                    );
                }
            }

            // =========================================================
            // ATRIBUTOS
            // =========================================================

            System.out.println("\n=== ATRIBUTOS DECLARADOS ===");

            Field[] atributos = classe.getDeclaredFields();

            if (atributos.length == 0) {
                System.out.println("Nenhum atributo encontrado.");
            } else {
                for (Field atributo : atributos) {

                    String modificadores =
                            Modifier.toString(atributo.getModifiers());

                    String identificacao =
                            Modifier.isPrivate(atributo.getModifiers())
                                    ? " [ATRIBUTO PRIVADO]"
                                    : "";

                    System.out.println(
                            modificadores
                                    + " "
                                    + atributo.getType().getSimpleName()
                                    + " "
                                    + atributo.getName()
                                    + identificacao
                    );
                }
            }

            // =========================================================
            // MÉTODOS PÚBLICOS
            // =========================================================

            System.out.println("\n=== MÉTODOS PÚBLICOS ===");

            Method[] metodosPublicos = classe.getMethods();

            if (metodosPublicos.length == 0) {
                System.out.println("Nenhum método público encontrado.");
            } else {
                for (Method metodo : metodosPublicos) {

                    System.out.println(
                            metodo.getName()
                                    + "("
                                    + formatarParametros(metodo.getParameterTypes())
                                    + ")"
                                    + " -> "
                                    + metodo.getReturnType().getSimpleName()
                                    + " | declarado em: "
                                    + metodo.getDeclaringClass().getName()
                    );
                }
            }

            // =========================================================
            // MÉTODOS DECLARADOS NA PRÓPRIA CLASSE
            // =========================================================

            System.out.println("\n=== MÉTODOS DECLARADOS NA PRÓPRIA CLASSE ===");

            Method[] metodosDeclarados = classe.getDeclaredMethods();

            if (metodosDeclarados.length == 0) {
                System.out.println("Nenhum método declarado encontrado.");
            } else {
                for (Method metodo : metodosDeclarados) {

                    System.out.println(
                            Modifier.toString(metodo.getModifiers())
                                    + " "
                                    + metodo.getReturnType().getSimpleName()
                                    + " "
                                    + metodo.getName()
                                    + "("
                                    + formatarParametros(metodo.getParameterTypes())
                                    + ")"
                    );
                }
            }

            // =========================================================
            // DIFERENÇA ENTRE MÉTODOS PÚBLICOS E DECLARADOS
            // =========================================================

            System.out.println("\n=== DIFERENÇA ENTRE OS MÉTODOS ===");

            System.out.println(
                    "getMethods() mostra os métodos públicos da classe, "
                            + "incluindo métodos herdados."
            );

            System.out.println(
                    "getDeclaredMethods() mostra somente os métodos "
                            + "declarados diretamente na própria classe."
            );

            System.out.println(
                    "\nA Reflection permite realizar essa inspeção "
                            + "em tempo de execução sem conhecer previamente "
                            + "a estrutura interna da classe."
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "\nErro: a classe '" + nomeClasse + "' não foi encontrada."
            );

        } finally {
            scanner.close();
        }
    }

    /**
     * Formata os tipos dos parâmetros de um construtor ou método.
     */
    private static String formatarParametros(Class<?>[] parametros) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < parametros.length; i++) {

            resultado.append(parametros[i].getTypeName());

            if (i < parametros.length - 1) {
                resultado.append(", ");
            }
        }

        return resultado.toString();
    }
}

/**
 * Classe de exemplo utilizada para demonstrar a inspeção por Reflection.
 */
class PessoaReflexao {

    private String nome;
    private int idade;

    public PessoaReflexao() {
    }

    public PessoaReflexao(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void apresentar() {
        System.out.println("Olá, meu nome é " + nome);
    }

    private void metodoPrivado() {
        System.out.println("Este método é privado.");
    }
}
