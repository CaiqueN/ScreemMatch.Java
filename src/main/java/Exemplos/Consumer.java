package Exemplos;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> {

    /**
     * Executa esta operação sobre o argumento informado.
     *
     * @param t o argumento de entrada
     */
    void accept(T t);

    /**
     * Retorna um {@code Consumer} composto que executa, em sequência, esta
     * operação seguida da operação {@code after}. Se a execução de qualquer
     * uma das operações lançar uma exceção, ela é repassada ao chamador da
     * operação composta. Se esta operação lançar uma exceção, a operação
     * {@code after} não será executada.
     *
     * @param after a operação a ser executada após esta operação
     * @return um {@code Consumer} composto que executa, em sequência, esta
     * operação seguida da operação {@code after}
     * @throws NullPointerException se {@code after} for nulo
     */
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
