package Exemplos;

import java.util.List;
import java.util.ArrayList;

@FunctionalInterface
interface Operacao {
    int executar(int a, int b);
}

public class ExemploLambda {
    public static void main(String[] args) {

        // Antes do Java 8: classe anônima
        Operacao somaAnonima = new Operacao() {
            @Override
            public int executar(int a, int b) {
                return a + b;
            }
        };
        System.out.println("Classe anônima: " + somaAnonima.executar(5, 3)); // 8

        // Com lambda: muito mais conciso
        Operacao soma = (a, b) -> a + b;
        Operacao multiplicacao = (a, b) -> a * b;

        System.out.println("Lambda soma: " + soma.executar(5, 3));           // 8
        System.out.println("Lambda multiplicação: " + multiplicacao.executar(5, 3)); // 15

        // Lambda com forEach em coleção
        List<String> series = new ArrayList<>();
        series.add("Breaking Bad");
        series.add("Dark");
        series.add("Never Have I Ever");

        // forEach com lambda
        series.forEach(s -> System.out.println("Série: " + s));

        // Equivalente usando method reference (::)
        series.forEach(System.out::println);
    }
}
