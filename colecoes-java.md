# Coleções em Java

As coleções são estruturas de dados fundamentais no Java que permitem armazenar e manipular conjuntos de elementos de forma eficiente. Elas fazem parte do pacote `java.util` e oferecem uma variedade de classes e interfaces para organizar dados de diferentes maneiras.

## Principais interfaces

### List
Coleção **ordenada** que permite elementos duplicados. Os elementos são acessados por índices.

### Set
Coleção que **não permite elementos duplicados** e normalmente não possui ordem definida.

### Queue
Representa uma **fila**: elementos são adicionados no final e removidos do início.

### Map
Coleção de **pares chave-valor**, onde cada chave é única e mapeada para um valor correspondente.

## Classes concretas mais comuns

| Interface | Implementações |
|-----------|---------------|
| `List`    | `ArrayList`, `LinkedList` |
| `Set`     | `HashSet`, `TreeSet` |
| `Map`     | `HashMap`, `TreeMap` |

## Usando List na prática

`List` é a interface mais utilizada. Ela mantém a **ordem de inserção**, permite **duplicatas** e facilita a iteração com `for-each`.

```java
import java.util.List;
import java.util.ArrayList;

public class ExemploList {
    public static void main(String[] args) {
        List<Integer> numeros = new ArrayList<>();

        // Adicionando elementos
        numeros.add(10);
        numeros.add(20);
        numeros.add(30);

        // Acessando por índice
        System.out.println(numeros.get(0)); // 10
        System.out.println(numeros.get(1)); // 20
        System.out.println(numeros.get(2)); // 30

        // Percorrendo com for-each
        for (Integer numero : numeros) {
            System.out.println(numero);
        }

        // Removendo pelo índice
        numeros.remove(1); // remove o 20

        System.out.println("Tamanho: " + numeros.size()); // 2
    }
}
```

## Usando Set na prática

`Set` é útil quando você quer garantir que **não existam elementos repetidos** na coleção. Ao tentar adicionar um valor duplicado, ele simplesmente é ignorado.

```java
import java.util.Set;
import java.util.HashSet;

public class ExemploSet {
    public static void main(String[] args) {
        Set<String> generos = new HashSet<>();

        // Adicionando elementos
        generos.add("Drama");
        generos.add("Comédia");
        generos.add("Drama"); // duplicata — será ignorada

        // O Set terá apenas 2 elementos
        System.out.println("Total: " + generos.size()); // 2

        // Verificando se um elemento existe
        System.out.println(generos.contains("Drama")); // true

        // Percorrendo com for-each (sem ordem garantida)
        for (String genero : generos) {
            System.out.println(genero);
        }

        // Removendo um elemento
        generos.remove("Comédia");
    }
}
```

## Usando Queue na prática

`Queue` representa uma **fila** (FIFO — primeiro a entrar, primeiro a sair). Usada quando a ordem de processamento dos elementos importa.

```java
import java.util.Queue;
import java.util.LinkedList;

public class ExemploQueue {
    public static void main(String[] args) {
        Queue<String> fila = new LinkedList<>();

        // Adicionando elementos no final da fila
        fila.offer("Episódio 1");
        fila.offer("Episódio 2");
        fila.offer("Episódio 3");

        // Vendo o primeiro sem remover
        System.out.println(fila.peek()); // Episódio 1

        // Removendo e retornando o primeiro elemento
        System.out.println(fila.poll()); // Episódio 1
        System.out.println(fila.poll()); // Episódio 2

        System.out.println("Restantes: " + fila.size()); // 1
    }
}
```

## Usando Map na prática

`Map` é ideal quando você precisa associar uma chave a um valor — como um dicionário. Cada chave é única, mas os valores podem se repetir.

```java
import java.util.Map;
import java.util.HashMap;

public class ExemploMap {
    public static void main(String[] args) {
        Map<String, Integer> notas = new HashMap<>();

        // Adicionando pares chave-valor
        notas.put("Maria", 9);
        notas.put("João", 7);
        notas.put("Ana", 10);

        // Acessando pelo chave
        System.out.println(notas.get("Maria")); // 9

        // Verificando se uma chave existe
        System.out.println(notas.containsKey("João")); // true

        // Percorrendo o Map
        for (Map.Entry<String, Integer> entrada : notas.entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        }

        // Removendo uma entrada
        notas.remove("João");

        System.out.println("Total de alunos: " + notas.size()); // 2
    }
}
```

## Iterando coleções: for-each vs for tradicional

### For-each (enhanced for loop)

Para a maioria dos casos — quando você quer apenas **ler ou processar** cada elemento — o for-each é a escolha certa.

```java
List<String> series = new ArrayList<>();
series.add("Breaking Bad");
series.add("Dark");
series.add("Severance");

// Simples, legível, sem risco de índice errado
for (String serie : series) {
    System.out.println(serie);
}
```

**Vantagens:**
- Mais legível: expressa a intenção diretamente ("para cada elemento, faça X")
- Sem gerenciamento de índice — elimina erros de `IndexOutOfBoundsException`
- Funciona com qualquer `Iterable`: `List`, `Set`, `Queue` e até arrays

### For tradicional (com índice)

Use quando precisar da **posição** do elemento dentro da lista:

```java
List<String> series = List.of("Breaking Bad", "Dark", "Severance");

for (int i = 0; i < series.size(); i++) {
    System.out.println("Posição " + i + ": " + series.get(i));
}
// Posição 0: Breaking Bad
// Posição 1: Dark
// Posição 2: Severance
```

Também é útil para **iterar de trás para frente**:

```java
for (int i = series.size() - 1; i >= 0; i--) {
    System.out.println(series.get(i));
}
// Severance → Dark → Breaking Bad
```

### Removendo elementos durante a iteração

**Nunca remova com for-each** — causa `ConcurrentModificationException`:

```java
// ERRADO: lança ConcurrentModificationException
for (String serie : series) {
    if (serie.equals("Dark")) {
        series.remove(serie); // erro em tempo de execução!
    }
}
```

Use `Iterator` para remover com segurança:

```java
Iterator<String> it = series.iterator();
while (it.hasNext()) {
    String serie = it.next();
    if (serie.equals("Dark")) {
        it.remove(); // seguro
    }
}
```

Ou, de forma mais moderna com `removeIf`:

```java
series.removeIf(serie -> serie.equals("Dark"));
```

### Resumo: qual usar?

| Situação | Recomendação |
|---|---|
| Apenas ler/processar elementos | `for-each` |
| Precisar do índice | `for` tradicional |
| Iterar de trás para frente | `for` tradicional |
| Remover elementos durante a iteração | `Iterator` ou `removeIf` |

## Quando usar cada uma

- Use **`List`** quando a ordem importa ou você precisa acessar elementos por posição.
- Use **`Set`** quando não pode haver duplicatas e a ordem não importa.
- Use **`Map`** quando precisar associar uma chave a um valor para busca rápida.

## Por que coleções são importantes

- Armazenar dados em memória de forma organizada
- Realizar buscas, ordenações e filtragens com facilidade
- Manipular grandes volumes de dados de forma eficiente e elegante

---

## Funções Lambda

Introduzidas no **Java 8**, as lambdas permitem passar um comportamento como argumento de forma compacta, sem precisar criar classes separadas.

### Interface Funcional

Uma interface funcional tem **exatamente um método abstrato**. A anotação `@FunctionalInterface` garante isso em tempo de compilação:

```java
@FunctionalInterface
public interface Operacao {
    int executar(int a, int b);
}
```

### Antes das lambdas: classe anônima

Para implementar uma interface funcional, era preciso criar uma classe anônima — verbosa e difícil de ler:

```java
Operacao soma = new Operacao() {
    @Override
    public int executar(int a, int b) {
        return a + b;
    }
};
System.out.println(soma.executar(5, 3)); // 8
```

### Com lambda: conciso e legível

A mesma implementação em uma linha:

```java
Operacao soma = (a, b) -> a + b;
System.out.println(soma.executar(5, 3)); // 8
```

A sintaxe é: `(parâmetros) -> expressão`

### Lambda com forEach

O `forEach` recebe um `Consumer<T>`, que é uma interface funcional. Por isso aceita lambda:

```java
List<String> series = List.of("Breaking Bad", "Dark", "Never Have I Ever");

// com lambda
series.forEach(s -> System.out.println(s));

// com method reference (:: ) — equivalente quando há um único parâmetro e uma única chamada
series.forEach(System.out::println);
```

### Lambda aninhada (como no projeto)

Quando uma coleção contém outra coleção, é possível aninhar lambdas:

```java
// Para cada temporada t, percorre seus episódios e imprime o título
temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
```

Equivalente ao duplo `for`:

```java
for (DadosTemporadas t : temporadas) {
    for (DadosEpisodio e : t.episodios()) {
        System.out.println(e.titulo());
    }
}
```

### Quando usar lambda vs for

| Situação | Recomendação |
|---|---|
| Operação simples (imprimir, filtrar) | Lambda |
| Lógica complexa com vários passos | `for` tradicional |
| Precisar de `break` ou `continue` | `for` tradicional |
| Encadear operações (filter + map + collect) | Lambda com Streams |
