package cmd.core;

import cmd.annotations.Command;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Descobre metodos anotados com @Command em objetos registrados e mantem
 * um registro nome -> CommandDefinition.
 */
public class CommandRegistry {

    private final Map<String, CommandDefinition> commands = new LinkedHashMap<>();

    public <T> void register(T commandHolder) {
        Class<?> type = commandHolder.getClass();
        for (Method method : type.getDeclaredMethods()) {
            Command annotation = method.getAnnotation(Command.class);
            if (annotation == null) {
                continue;
            }
            String name = annotation.name();
            if (commands.containsKey(name)) {
                throw new IllegalStateException("Comando duplicado: '" + name + "'");
            }
            commands.put(name, new CommandDefinition(name, annotation.description(), commandHolder, method));
        }
    }

    public CommandDefinition get(String name) {
        CommandDefinition definition = commands.get(name);
        if (definition == null) {
            throw new CommandValidationException("Comando nao encontrado: '" + name + "'");
        }
        return definition;
    }

    public Collection<CommandDefinition> listAll() {
        return commands.values();
    }
}
