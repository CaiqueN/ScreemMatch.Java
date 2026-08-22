package br.com.alura.exercicio;

import java.util.*;

public class LambdaExercises {
    public static void main(String[] args) {
        List<String> nomes = Arrays.asList("Lucas", "Maria", "João", "Ana");
        nomes.sort((a, b) -> a.compareTo(b));
        System.out.println(nomes);  // Resultado: [Ana, João, Lucas, Maria]
    }
}
