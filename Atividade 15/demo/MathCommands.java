package cmd.demo;

import cmd.annotations.Command;

public class MathCommands {

    @Command(name = "sum", description = "Soma dois inteiros")
    public int sum(int a, int b) {
        return a + b;
    }

    @Command(name = "multiply", description = "Multiplica dois numeros double")
    public double multiply(double a, double b) {
        return a * b;
    }
}
