package Exemplos;

import java.util.List;
import java.util.ArrayList;

public class ExemploConsumer {
    public static void main(String[] args) {

        // Exemplo 1: Consumer<String> - T vira String
        Consumer<String> imprimir = s -> System.out.println("Recebido: " + s);
        imprimir.accept("Breaking Bad");

        // Exemplo 2: Consumer<Integer> - T vira Integer (outro tipo, mesma interface)
        Consumer<Integer> imprimirDobro = numero -> System.out.println("Dobro: " + (numero * 2));
        imprimirDobro.accept(21);

        // Exemplo 3: tentar misturar tipos NÃO compila (comentado de propósito)
        // imprimir.accept(123); // erro: incompatible types

        // Exemplo 4: andThen encadeando duas ações sobre o MESMO valor
        Consumer<String> emMaiusculo = s -> System.out.println(s.toUpperCase());
        Consumer<String> tamanho = s -> System.out.println("Tamanho: " + s.length());

        Consumer<String> combinado = imprimir.andThen(emMaiusculo).andThen(tamanho);
        combinado.accept("dark");
        // saída:
        // Recebido: dark
        // DARK
        // Tamanho: 4

        // Exemplo 5: Consumer usado com forEach (é exatamente isso que forEach espera)
        List<String> series = new ArrayList<>();
        series.add("Breaking Bad");
        series.add("Dark");
        series.add("Never Have I Ever");

        Consumer<String> imprimirSerie = serie -> System.out.println("Série: " + serie);
        series.forEach(imprimirSerie::accept);

        // Exemplo 6: <? super T> - Consumer de um tipo mais genérico (Object) funciona
        // como "after" para um Consumer<String>, porque Object é super de String
        Consumer<Object> imprimirGenerico = obj -> System.out.println("Objeto: " + obj);
        Consumer<String> outroCombinado = imprimir.andThen(imprimirGenerico);
        outroCombinado.accept("Wednesday");
    }
}
