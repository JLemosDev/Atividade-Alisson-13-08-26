import java.lang.reflect.Field;

public class GeradorSql {

    public static String gerarInsert(Object objeto) {

        Class<?> classe = objeto.getClass();

        Tabela tabela = classe.getAnnotation(Tabela.class);

        if (tabela == null) {
            throw new IllegalArgumentException(
                    "A classe não possui a anotação @Tabela."
            );
        }

        String nomeTabela = tabela.nome();

        StringBuilder colunas = new StringBuilder();
        StringBuilder valores = new StringBuilder();

        Field[] campos = classe.getDeclaredFields();

        boolean primeiro = true;

        for (Field campo : campos) {

            Coluna coluna = campo.getAnnotation(Coluna.class);

    
            if (coluna == null) {
                continue;
            }

            campo.setAccessible(true);

            try {

                Object valor = campo.get(objeto);

                if (!primeiro) {
                    colunas.append(", ");
                    valores.append(", ");
                }

                colunas.append(coluna.nome());
                valores.append(formatarValor(valor));

                primeiro = false;

            } catch (IllegalAccessException e) {

                throw new RuntimeException(
                        "Erro ao acessar o campo: "
                                + campo.getName(),
                        e
                );
            }
        }

        return "INSERT INTO "
                + nomeTabela
                + " ("
                + colunas
                + ") VALUES ("
                + valores
                + ");";
    }

    private static String formatarValor(Object valor) {

        if (valor == null) {
            return "NULL";
        }

        if (valor instanceof Number
                || valor instanceof Boolean) {

            return valor.toString();
        }

        String texto = valor.toString();

        texto = texto.replace("'", "''");

        return "'" + texto + "'";
    }
}
