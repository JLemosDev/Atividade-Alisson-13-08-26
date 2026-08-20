package di.container;

import di.annotations.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Container de Injecao de Dependencias simplificado.
 *
 * Suporta:
 *  - Registro de implementacoes concretas para tipos/interfaces (bind).
 *  - Injecao via construtor anotado com @Inject.
 *  - Injecao via atributos anotados com @Inject.
 *  - Deteccao de dependencia circular.
 *  - Deteccao de dependencia nao registrada/nao resolvivel.
 *
 * Escopo: cada tipo resolvido vira um "singleton" dentro do container
 * (fica cacheado em `singletons`), de forma parecida com o escopo padrao
 * de beans no Spring.
 */
public class DIContainer {

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();
    private final Map<Class<?>, Object> singletons = new HashMap<>();

    /** Registra uma implementacao concreta para um tipo (pode ser interface ou classe). */
    public <T> void register(Class<T> type, Class<? extends T> implementation) {
        bindings.put(type, implementation);
    }

    /** Registra uma classe concreta "para si mesma" (facilidade para classes sem interface). */
    public <T> void register(Class<T> concreteType) {
        bindings.put(concreteType, concreteType);
    }

    public <T> T resolve(Class<T> type) {
        return resolve(type, new ArrayDeque<>());
    }

    @SuppressWarnings("unchecked")
    private <T> T resolve(Class<T> type, Deque<Class<?>> resolutionPath) {
        if (resolutionPath.contains(type)) {
            throw new CircularDependencyException(
                "Dependencia circular detectada: " + buildCyclePath(resolutionPath, type));
        }

        if (singletons.containsKey(type)) {
            return (T) singletons.get(type);
        }

        Class<?> implementation = bindings.getOrDefault(type, type);

        if (implementation.isInterface() || isAbstract(implementation)) {
            throw new DependencyNotFoundException(
                "Nenhuma implementacao registrada para o tipo " + type.getName() +
                ". Use container.register(" + type.getSimpleName() + ".class, SuaImpl.class)");
        }

        resolutionPath.push(type);
        try {
            T instance = instantiate((Class<T>) implementation, resolutionPath);
            injectFields(instance, resolutionPath);
            singletons.put(type, instance);
            return instance;
        } finally {
            resolutionPath.pop();
        }
    }

    private boolean isAbstract(Class<?> c) {
        return java.lang.reflect.Modifier.isAbstract(c.getModifiers());
    }

    @SuppressWarnings("unchecked")
    private <T> T instantiate(Class<T> implementation, Deque<Class<?>> resolutionPath) {
        Constructor<?> chosen = findInjectableConstructor(implementation);

        try {
            if (chosen.getParameterCount() == 0) {
                chosen.setAccessible(true);
                return (T) chosen.newInstance();
            }

            Class<?>[] paramTypes = chosen.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = resolve(paramTypes[i], resolutionPath);
            }
            chosen.setAccessible(true);
            return (T) chosen.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(
                "Falha ao instanciar " + implementation.getName() + " via reflection", e);
        }
    }

    private Constructor<?> findInjectableConstructor(Class<?> implementation) {
        Constructor<?>[] constructors = implementation.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                return constructor;
            }
        }

        try {
            return implementation.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            if (constructors.length == 1) {
                return constructors[0]; // unico construtor disponivel, mesmo sem @Inject
            }
            throw new DependencyNotFoundException(
                implementation.getName() + " precisa de um construtor sem argumentos " +
                "ou de um construtor unico/anotado com @Inject");
        }
    }

    private <T> void injectFields(T instance, Deque<Class<?>> resolutionPath) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }
            Object dependency = resolve(field.getType(), resolutionPath);
            field.setAccessible(true);
            try {
                field.set(instance, dependency);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Nao foi possivel injetar o campo " + field.getName(), e);
            }
        }
    }

    private String buildCyclePath(Deque<Class<?>> resolutionPath, Class<?> repeated) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> c : resolutionPath) {
            sb.insert(0, c.getSimpleName() + " -> ");
        }
        sb.append(repeated.getSimpleName());
        return sb.toString();
    }
}
