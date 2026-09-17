 # Lambdas, Interfaces Funcionais e Generics em Java

Anotações de estudo consolidando as dúvidas e exemplos trabalhados sobre **interfaces funcionais**, **lambdas**, **generics** e conceitos relacionados (`Math`, `StringBuilder`, `equals`).

---

## 1. Interface Funcional (`@FunctionalInterface`)

Uma interface funcional é uma interface com **exatamente um método abstrato** — por isso pode ser implementada com uma lambda.

```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);                 // único método abstrato (SAM)

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

### Por que só pode ter UM método abstrato?

Uma lambda não tem nome — é um bloco de código anônimo. O compilador precisa saber **exatamente qual método** ela está implementando. Se houvesse dois métodos abstratos, seria ambíguo (a lambda implementaria qual dos dois?).

```java
@FunctionalInterface
interface Palindromo {
}
```
❌ **Erro de compilação**: `no abstract method found`. Faltou declarar o método:
```java
@FunctionalInterface
interface Palindromo {
    boolean verificar(String srt);
}
```

> A anotação `@FunctionalInterface` não é obrigatória, mas funciona como uma **trava de segurança**: se alguém adicionar um segundo método abstrato por engano, o compilador acusa erro na hora.

> Métodos `default` e `static` **não contam** na regra — podem existir quantos você quiser (ex: `andThen` no `Consumer`).

---

## 2. Generics — pra que servem

Generics resolvem o problema de reutilizar código para vários tipos **sem perder segurança de tipo**.

### Sem generics (jeito antigo, usando `Object`)

```java
class Caixa {
    private Object conteudo;
    void guardar(Object o) { conteudo = o; }
    Object pegar() { return conteudo; }
}
```

```java
Caixa caixa = new Caixa();
caixa.guardar("Breaking Bad");
String serie = (String) caixa.pegar(); // precisa de CAST manual

caixa.guardar(123);                    // compila, mas é o tipo errado
String s = (String) caixa.pegar();     // ClassCastException em RUNTIME 💥
```

### Com generics

```java
class Caixa<T> {
    private T conteudo;
    void guardar(T o) { conteudo = o; }
    T pegar() { return conteudo; }
}
```

```java
Caixa<String> caixa = new Caixa<>();
caixa.guardar("Breaking Bad");
String serie = caixa.pegar();  // sem cast

caixa.guardar(123);            // ERRO DE COMPILAÇÃO — nem deixa rodar
```

### Resumo

| Sem generics (`Object`)              | Com generics (`T`)                          |
|---------------------------------------|----------------------------------------------|
| Precisa de cast manual                 | Sem cast, tipo já vem correto                |
| Erro de tipo só aparece em runtime     | Erro de tipo é pego em **tempo de compilação** |
| Um código serve pra tudo, mas sem segurança | Um código serve pra tudo, **com** segurança |

> `T` **não** significa "aceita qualquer tipo ao mesmo tempo". A interface/classe é genérica (reutilizável), mas **cada instância** fica travada em um tipo só: `Consumer<String>` só aceita `String`; `Consumer<Integer>` só aceita `Integer`.

### `<? super T>` — wildcard no `andThen`

```java
default Consumer<T> andThen(Consumer<? super T> after) { ... }
```

Permite que `after` seja um `Consumer` de `T` **ou de qualquer superclasse de `T`**. Ex: se `T` é `String`, um `Consumer<Object>` também serve, porque `Object` é super de `String`.

```java
Consumer<String> imprimir = s -> System.out.println(s);
Consumer<Object> imprimirGenerico = obj -> System.out.println("Objeto: " + obj);

Consumer<String> combinado = imprimir.andThen(imprimirGenerico); // funciona!
```

---

## 3. `andThen` — encadeando ações

`andThen` = **"e então"**. Executa a operação atual e, em seguida, executa outra sobre o **mesmo valor**.

```java
Consumer<String> imprimir = s -> System.out.println(s);
Consumer<String> emMaiusculo = s -> System.out.println(s.toUpperCase());

Consumer<String> combinado = imprimir.andThen(emMaiusculo);
combinado.accept("dark");
// saída:
// dark
// DARK
```

> O `t` dentro do `andThen` **não** é decidido ali — já veio fixado da declaração da interface (`Consumer<T>`). Ele só serve para repassar o mesmo valor de entrada para as duas operações encadeadas.

---

## 4. Lambda + `forEach`

`forEach` (de `List`) **espera um `Consumer`** como argumento:

```java
default void forEach(Consumer<? super T> action) {
    for (T t : this) {
        action.accept(t);
    }
}
```

Ou seja, qualquer lambda passada pro `forEach` é, na prática, uma implementação de `accept(T t)`:

```java
series.forEach(s -> System.out.println("Série: " + s));
series.forEach(System.out::println); // equivalente com method reference
```

### Boas práticas de nomes em lambda

| Situação | Recomendação |
|----------|--------------|
| Lambda curta, tipo óbvio pelo contexto | `s`, `e`, `i` são aceitáveis (`.map(s -> s.toUpperCase())`) |
| Lambda com mais de uma linha / lógica de negócio | Prefira nome descritivo (`serie`, `episodio`, `dado`) |

---

## 5. Classe Abstrata vs Interface

Classe abstrata é um **molde incompleto**: compartilha código comum entre classes parecidas, mas obriga as subclasses a implementar certos comportamentos.

```java
abstract class Titulo {
    protected String nome;
    protected double avaliacao;

    void exibirFicha() {                 // método pronto, herdado
        System.out.println(nome + " - nota: " + avaliacao);
    }

    abstract void assistir();            // sem corpo — obrigatório implementar
}

class Serie extends Titulo {
    void assistir() { System.out.println("Assistindo por temporadas..."); }
}

class Filme extends Titulo {
    void assistir() { System.out.println("Assistindo de uma vez só..."); }
}
```

| | Classe abstrata | Interface |
|---|---|---|
| Instanciável? | Não | Não |
| Tem estado (atributos)? | Sim | Não (só constantes) |
| Tem construtor? | Sim | Não |
| Métodos prontos + abstratos misturados? | Sim | Sim (com `default`) |
| Herança múltipla? | Não (só uma classe abstrata) | Sim (várias interfaces) |

---

## 6. `Math.sqrt` — otimização de número primo

`Math` é uma classe utilitária (`java.lang`, sem `import`), com métodos `static` (`sqrt`, `abs`, `max`, `min`, `pow`, `random`).

```java
for (int i = 2; i <= Math.sqrt(n); i++) {
    if (n % i == 0)
        return false;
}
```

**Por que só testar até `√n`?** Divisores vêm em pares que se multiplicam para dar `n`. Exemplo com `n = 36`:

```
1 × 36
2 × 18
3 × 12
4 × 9
6 × 6   ← raiz quadrada, ponto de virada
9 × 4  (repete o par anterior invertido)
```

Se não achou divisor até `√n`, não tem por que continuar — não vai achar nenhum novo daí pra frente. Isso reduz a complexidade de `O(n)` para `O(√n)`.

---

## 7. `StringBuilder` e `append`

`StringBuilder` monta strings de forma eficiente, sem criar objetos `String` intermediários a cada concatenação (diferente de `+=` em loop).

```java
StringBuilder resultado = new StringBuilder();
for (char c : s.toCharArray()) {
    resultado.append(c);          // "adiciona no final"
}
resultado.toString();             // converte de volta pra String
```

`append` é método de **instância** de `java.lang.StringBuilder` — só pode ser chamado em cima de um objeto (`resultado.append(...)`), diferente de `Math.sqrt` que é `static`.

### Exemplo: `toUpperCase` manual usando `char`

```java
Transformar toUpperCaseManual = s -> {
    StringBuilder resultado = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (c >= 'a' && c <= 'z') {
            c = (char) (c - 'a' + 'A'); // desloca minúscula → maiúscula (ASCII)
        }
        resultado.append(c);
    }
    return resultado.toString();
};
```

---

## 8. `equals` — comparar, não separar

`equals` **compara conteúdo** de dois objetos, retornando `true`/`false`. Não confundir com `split()`, que **separa** uma string em pedaços.

```java
"radar".equals("radar")   // true
"a,b,c".split(",")        // ["a", "b", "c"]
```

⚠️ Para `String`, sempre use `.equals()`, nunca `==`:

```java
String a = new String("java");
String b = new String("java");

a == b        // false — objetos diferentes na memória
a.equals(b)   // true — mesmo conteúdo
```

### Exemplo: verificar palíndromo

```java
Palindromo palindromo = srt -> srt.equals(new StringBuilder(srt).reverse().toString());

palindromo.verificar("radar"); // true  → invertido = "radar"
palindromo.verificar("java");  // false → invertido = "avaj"
```

---

## 9. Outros exercícios com lambda

### Ordenação com `Comparator` (lambda)

```java
List<String> nomes = Arrays.asList("Lucas", "Maria", "João", "Ana");
nomes.sort((a, b) -> a.compareTo(b));
System.out.println(nomes);  // [Ana, João, Lucas, Maria]
```

### Lançamento de exceção dentro de uma lambda

```java
interface Divisor {
    int dividir(int a, int b) throws ArithmeticException;
}

Divisor divisor = (a, b) -> {
    if (b == 0) throw new ArithmeticException("Divisão por zero");
    return a / b;
};

try {
    System.out.println(divisor.dividir(10, 2)); // 5
    System.out.println(divisor.dividir(10, 0)); // lança exceção
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());          // "Divisão por zero"
}
```

---

## Resumo Visual

```
Interfaces Funcionais e Lambdas
│
├── @FunctionalInterface
│   ├── exatamente 1 método abstrato (SAM)
│   └── default/static → sem limite
│
├── Generics <T>
│   ├── reuso de código sem perder segurança de tipo
│   ├── erro pego em compilação, não em runtime
│   └── <? super T> → aceita T ou supertipos de T
│
├── Consumer<T>
│   ├── accept(T t)          → ação sobre um valor, sem retorno
│   └── andThen(Consumer)    → encadeia duas ações no mesmo valor
│
├── forEach(Consumer<? super T> action)
│   └── lambda passada = implementação de accept()
│
└── Classe abstrata
    ├── estado + construtor + métodos prontos/abstratos
    └── herança simples (vs múltipla em interface)
```

---

## Pontos-chave para memorizar

- Lambda só funciona com interface de **um único método abstrato**.
- Generics trocam `Object` + cast manual por **segurança de tipo em compilação**.
- `T` é fixado por instância, não é "qualquer tipo simultaneamente".
- `forEach`, `Comparator.compare`, `Consumer.accept` — todo método que uma lambda "vira" é, na real, a implementação de um método de interface funcional.
- `equals` compara conteúdo; `==` compara referência/identidade de objeto.
- `Math` e `StringBuilder`: métodos `static` (chama pela classe) vs métodos de instância (chama pelo objeto).

---

## O que aprendemos (aula de origem — API, Git e Lambdas)

Nessa aula, você aprendeu sobre:

- **Desenvolvimento Colaborativo**: a importância do desenvolvimento colaborativo em projetos de programação e como ferramentas como o Git facilitam esse processo.
- **APIs e Consultas Detalhadas**: como trabalhar com APIs para detalhar informações e obter consultas mais específicas.
- **Anotações `@JsonAlias` e `@JsonIgnoreProperties`**: a importância de usar essas anotações para mapear a API para a aplicação.
- **Criação de métodos para interação do usuário**: um método para exibir o menu e interagir com o usuário, permitindo que digitem o nome da série que desejam pesquisar.
- **Manipulação de dados de uma API**: como importar e manipular dados de uma API, neste caso, dados de séries de TV.
- **Manipulação de Strings para acessar uma API**: como manipular strings para criar endereços que a API compreenderá e retornará os dados desejados.
- **Introdução aos Lambdas**: Lambda Expressions em Java, conhecidas como funções anônimas, usadas para escrever código mais eficiente.
