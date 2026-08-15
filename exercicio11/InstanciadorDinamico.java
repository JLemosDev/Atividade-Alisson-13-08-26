import java.lang.reflect.Constructor;

public class InstanciadorDinamico {

    public static Object criarObjeto(String nomeClasse, Object... argumentos)
            throws Exception {

        Class<?> classe = Class.forName(nomeClasse);

        Constructor<?>[] construtores =
                classe.getDeclaredConstructors();

        for (Constructor<?> construtor : construtores) {

            Class<?>[] tiposParametros =
                    construtor.getParameterTypes();

            if (tiposParametros.length != argumentos.length) {
                continue;
            }

            boolean compativel = true;

            for (int i = 0; i < tiposParametros.length; i++) {

                if (!tipoCompativel(
                        tiposParametros[i],
                        argumentos[i])) {

                    compativel = false;
                    break;
                }
            }

            if (compativel) {

                construtor.setAccessible(true);

                return construtor.newInstance(argumentos);
            }
        }

        throw new NoSuchMethodException(
                "Nenhum construtor compatível foi encontrado."
        );
    }

    private static boolean tipoCompativel(
            Class<?> tipoEsperado,
            Object argumento) {

        if (argumento == null) {
            return !tipoEsperado.isPrimitive();
        }

        Class<?> tipoRecebido = argumento.getClass();

        if (!tipoEsperado.isPrimitive()) {
            return tipoEsperado.isAssignableFrom(tipoRecebido);
        }

        if (tipoEsperado == int.class) {
            return tipoRecebido == Integer.class;
        }

        if (tipoEsperado == long.class) {
            return tipoRecebido == Long.class;
        }

        if (tipoEsperado == double.class) {
            return tipoRecebido == Double.class;
        }

        if (tipoEsperado == float.class) {
            return tipoRecebido == Float.class;
        }

        if (tipoEsperado == boolean.class) {
            return tipoRecebido == Boolean.class;
        }

        if (tipoEsperado == char.class) {
            return tipoRecebido == Character.class;
        }

        if (tipoEsperado == byte.class) {
            return tipoRecebido == Byte.class;
        }

        if (tipoEsperado == short.class) {
            return tipoRecebido == Short.class;
        }

        return false;
    }

    public static void main(String[] args) {

        try {

            String nomeClasse = "Produto";

            Object objeto = criarObjeto(
                    nomeClasse,
                    "Notebook",
                    3500.00
            );

            System.out.println("Objeto criado com sucesso!");
            System.out.println("Classe: "
                    + objeto.getClass().getName());

            System.out.println("Objeto: " + objeto);

        } catch (Exception e) {

            System.out.println(
                    "Erro ao criar o objeto: " + e.getMessage()
            );
        }
    }
}
