package cmd.core;

/**
 * Resultado generico da execucao de um comando.
 * Generics garante que quem chama execute() sabe o tipo do dado de retorno
 * (quando conhecido em tempo de compilacao) sem precisar fazer cast manual
 * espalhado pelo codigo cliente.
 */
public final class CommandResult<T> {

    private final boolean success;
    private final T data;
    private final String message;

    private CommandResult(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> CommandResult<T> ok(T data) {
        return new CommandResult<>(true, data, "OK");
    }

    public static <T> CommandResult<T> error(String message) {
        return new CommandResult<>(false, null, message);
    }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return success
                ? "CommandResult{OK, data=" + data + "}"
                : "CommandResult{ERROR, message='" + message + "'}";
    }
}
