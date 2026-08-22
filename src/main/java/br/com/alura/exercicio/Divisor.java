package br.com.alura.exercicio;

@FunctionalInterface
public interface Divisor {
    int dividir(int a, int b) throws ArithmeticException;
}
