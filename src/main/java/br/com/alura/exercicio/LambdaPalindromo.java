package br.com.alura.exercicio;

public class LambdaPalindromo {
    public static void main(String[] args) {
        Palindromo palindromo = srt -> srt.equals(new StringBuilder(srt).reverse().toString());
        System.out.println(palindromo.verificar("radar"));  // Resultado: true
        System.out.println(palindromo.verificar("java"));   // Resultado: false
    }
}
