public class Main {

    public static void main(String[] args) {

        Cliente cliente = new Cliente(
                1,
                "Maria",
                "maria@email.com",
                "Este campo será ignorado"
        );

        String sql = GeradorSql.gerarInsert(cliente);

        System.out.println("SQL gerado:");
        System.out.println(sql);
    }
}
