package br.com.alura.exercicio;

import java.util.Arrays;
import java.util.List;

public class ListaInteiros {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.
        (1, 2, 3, 4, 5);

        numeros.replaceAll(numero -> numero * 3);

        System.out.println(numeros); // Resultado: [3, 6, 9, 12, 15]
    }
}
