package br.com.alura.exercicio;

public class Lambda {
    public static void main(String[] args) {
        Multiplicacao multi = (a, b) -> (a * b);
        System.out.println(multi.multiplicacao(10,10));
    }
}
