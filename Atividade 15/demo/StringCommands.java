package cmd.demo;

import cmd.annotations.Command;

public class StringCommands {

    @Command(name = "upper", description = "Converte texto para maiusculas")
    public String upper(String text) {
        return text.toUpperCase();
    }

    @Command(name = "concat", description = "Concatena dois textos")
    public String concat(String a, String b) {
        return a + b;
    }
}
