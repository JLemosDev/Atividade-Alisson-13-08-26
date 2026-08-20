package cmd.core;

import java.lang.reflect.Method;

/**
 * Metadados de um comando descoberto via Reflection: nome, descricao,
 * instancia alvo e o Method a ser invocado.
 */
public final class CommandDefinition {

    private final String name;
    private final String description;
    private final Object target;
    private final Method method;

    public CommandDefinition(String name, String description, Object target, Method method) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.method = method;
        this.method.setAccessible(true);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Object getTarget() { return target; }
    public Method getMethod() { return method; }
    public Class<?>[] getParameterTypes() { return method.getParameterTypes(); }
}
