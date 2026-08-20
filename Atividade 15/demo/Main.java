package cmd.demo;

import cmd.core.CommandExecutor;
import cmd.core.CommandRegistry;
import cmd.core.CommandResult;

public class Main {
    public static void main(String[] args) {
        CommandRegistry registry = new CommandRegistry();
        registry.register(new MathCommands());
        registry.register(new StringCommands());

        System.out.println("== Comandos registrados ==");
        registry.listAll().forEach(c ->
            System.out.println(" - " + c.getName() + ": " + c.getDescription()));

        CommandExecutor executor = new CommandExecutor(registry);

        System.out.println("\n== Execucoes validas ==");
        CommandResult<Integer> sumResult = executor.execute("sum", 2, 3);
        System.out.println("sum(2, 3) -> " + sumResult);

        CommandResult<String> upperResult = executor.execute("upper", "java generics");
        System.out.println("upper('java generics') -> " + upperResult);

        System.out.println("\n== Erros de validacao ==");
        CommandResult<Object> wrongArity = executor.execute("sum", 1);
        System.out.println("sum(1) -> " + wrongArity);

        CommandResult<Object> wrongType = executor.execute("sum", "a", "b");
        System.out.println("sum(\"a\",\"b\") -> " + wrongType);

        CommandResult<Object> unknown = executor.execute("divide", 4, 2);
        System.out.println("divide(4,2) -> " + unknown);
    }
}
