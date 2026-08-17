import java.util.*;

public class UtilNumeros {

    public static double soma(Collection<? extends Number> numeros) {
        double total = 0;

        for (Number numero : numeros) {
            total += numero.doubleValue();
        }

        return total;
    }

    public static double media(Collection<? extends Number> numeros) {
        if (numeros.isEmpty()) {
            return 0;
        }

        return soma(numeros) / numeros.size();
    }

    public static <T extends Number & Comparable<T>> T maior(List<T> numeros) {
        if (numeros.isEmpty()) {
            return null;
        }

        T maior = numeros.get(0);

        for (T numero : numeros) {
            if (numero.compareTo(maior) > 0) {
                maior = numero;
            }
        }

        return maior;
    }

    public static void copiarNumeros(
            List<? extends Number> origem,
            List<? super Number> destino) {

        destino.addAll(origem);
    }

    public static void adicionarNumeros(
            List<? super Integer> lista) {

        lista.add(10);
        lista.add(20);
        lista.add(30);
    }

    public static void main(String[] args) {

        List<Integer> inteiros = Arrays.asList(10, 20, 30, 40, 50);

        System.out.println("Soma: " + soma(inteiros));
        System.out.println("Média: " + media(inteiros));
        System.out.println("Maior: " + maior(inteiros));

        List<Number> destino = new ArrayList<>();

        copiarNumeros(inteiros, destino);

        System.out.println("Lista copiada: " + destino);

        List<Integer> novosNumeros = new ArrayList<>();
        adicionarNumeros(novosNumeros);

        System.out.println("Números adicionados: " + novosNumeros);
    }
}
