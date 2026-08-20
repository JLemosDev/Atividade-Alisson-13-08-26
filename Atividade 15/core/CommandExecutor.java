package cmd.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Executa comandos registrados dinamicamente via Reflection, validando
 * quantidade e tipos de parametros antes de invocar o metodo alvo.
 */
public class CommandExecutor {

    private final CommandRegistry registry;

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPERS = Map.of(
            int.class, Integer.class,
            long.class, Long.class,
            double.class, Double.class,
            float.class, Float.class,
            boolean.class, Boolean.class,
            short.class, Short.class,
            byte.class, Byte.class,
            char.class, Character.class
    );

    public CommandExecutor(CommandRegistry registry) {
        this.registry = registry;
    }

    /**
     * Executa um comando pelo nome. O tipo de retorno e generico (T): quem
     * chama pode indicar o tipo esperado via inferencia, por exemplo:
     *   CommandResult<Integer> r = executor.execute("sum", 2, 3);
     */
    @SuppressWarnings("unchecked")
    public <T> CommandResult<T> execute(String commandName, Object... args) {
        CommandDefinition definition;
        try {
            definition = registry.get(commandName);
        } catch (CommandValidationException e) {
            return CommandResult.error(e.getMessage());
        }

        try {
            validateArguments(definition, args);
        } catch (CommandValidationException e) {
            return CommandResult.error(e.getMessage());
        }

        Method method = definition.getMethod();
        try {
            Object result = method.invoke(definition.getTarget(), args);
            return CommandResult.ok((T) result);
        } catch (IllegalAccessException e) {
            return CommandResult.error("Nao foi possivel acessar o comando: " + e.getMessage());
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            return CommandResult.error("Erro ao executar comando '" + commandName + "': " + cause.getMessage());
        }
    }

    private void validateArguments(CommandDefinition definition, Object[] args) {
        Class<?>[] expected = definition.getParameterTypes();

        if (args.length != expected.length) {
            throw new CommandValidationException(
                "Comando '" + definition.getName() + "' espera " + expected.length +
                " parametro(s), mas recebeu " + args.length);
        }

        for (int i = 0; i < expected.length; i++) {
            Class<?> expectedType = expected[i];
            Object actual = args[i];

            if (actual == null) {
                if (expectedType.isPrimitive()) {
                    throw new CommandValidationException(
                        "Parametro " + i + " do comando '" + definition.getName() +
                        "' nao pode ser nulo (esperado " + expectedType.getSimpleName() + ")");
                }
                continue;
            }

            Class<?> targetType = expectedType.isPrimitive()
                    ? PRIMITIVE_WRAPPERS.get(expectedType)
                    : expectedType;

            if (!targetType.isInstance(actual)) {
                throw new CommandValidationException(
                    "Parametro " + i + " do comando '" + definition.getName() +
                    "' deveria ser do tipo " + targetType.getSimpleName() +
                    ", mas recebeu " + actual.getClass().getSimpleName());
            }
        }
    }
}
