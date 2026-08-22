package br.com.alura.exercicio;

public class TransformarString {
    public static void main(String[] args) {
        Transformar toUpperCaseManual = s -> {
            StringBuilder resultado = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    c = (char) (c - 'a' + 'A'); // desloca a letra minúscula pra maiúscula
                }
                resultado.append(c);
            }
            return resultado.toString();
        };

        System.out.println(toUpperCaseManual.transformar("caique"));
    }
}
