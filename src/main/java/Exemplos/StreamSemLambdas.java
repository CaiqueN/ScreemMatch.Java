package Exemplos;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamSemLambdas {

    public static void main(String[] args) {
        List<String> nomes = Arrays.asList(
                "Nathalia",
                "Caique",
                "Bruninha",
                "Italo",
                "Pedroca"
        );

        nomes.stream()
                .sorted()
                .limit(3)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String nome) {
                        return nome.startsWith("N");
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String nome) {
                        return nome.toUpperCase();
                    }
                })
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String nome) {
                        System.out.println(nome);
                    }
                });
    }
}
