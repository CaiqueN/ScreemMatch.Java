# Static e Constantes em Java

## O que é uma constante?

Uma constante é um valor que **não muda** durante a execução do programa. Em Java, declaramos com `final`:

```java
private final String ENDERECO = "https://www.omdbapi.com/?t=";
```

Por convenção, o nome de constantes é escrito em **MAIÚSCULAS com underscore**.

---

## Por que dividir a URL em constantes?

Na classe `Principal`, a URL da API OMDB foi separada em duas constantes:

```java
private static final String ENDERECO = "https://www.omdbapi.com/?t=";
private static final String API_KEY = "&apikey=6585022c";
```

E montada assim no método:

```java
var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
```

**Vantagens:**
- Se a URL base mudar, altera em um lugar só
- Se a API_KEY mudar, altera em um lugar só
- O código fica mais legível — o nome da constante explica o que ela representa
- Esse princípio se chama **named constant**

---

## O que é `static`?

`static` define que o atributo ou método pertence à **classe**, não ao **objeto**.

### Sem `static` — pertence ao objeto:

```java
public class Principal {
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
}

Principal p1 = new Principal(); // cria cópia de ENDERECO para p1
Principal p2 = new Principal(); // cria cópia de ENDERECO para p2
// cada objeto tem sua própria cópia na memória
```

### Com `static` — pertence à classe:

```java
public class Principal {
    private static final String ENDERECO = "https://www.omdbapi.com/?t=";
}

Principal p1 = new Principal();
Principal p2 = new Principal();
// p1 e p2 apontam para o mesmo ENDERECO — existe uma única cópia na memória
```

---

## Comparação

| | `static` | sem `static` |
|---|---|---|
| Pertence a | classe | objeto |
| Cópias na memória | 1 | 1 por objeto criado |
| Acesso | `Classe.CONSTANTE` | `objeto.constante` |
| Quando usar | valor igual para todos os objetos | valor pode variar por objeto |

---

## Acessando sem instanciar

Com `static`, você pode acessar a constante direto pela classe, sem precisar de `new`:

```java
// sem precisar de new Principal()
System.out.println(Principal.ENDERECO); // "https://www.omdbapi.com/?t="
System.out.println(Principal.API_KEY);  // "&apikey=6585022c"
```

---

## O que obriga a instanciar na classe Principal?

`static` nas constantes **não impede** de instanciar a classe. O que exige o `new` são os atributos **sem** `static`:

```java
public class Principal {
    private Scanner scanner = new Scanner(System.in); // sem static → precisa de objeto
    private ConsumerAPI consumo = new ConsumerAPI();  // sem static → precisa de objeto

    private static final String ENDERECO = "...";    // static → não precisa de objeto
    private static final String API_KEY = "...";     // static → não precisa de objeto
}
```

`scanner` e `consumo` precisam de objeto porque cada instância pode ter seu próprio scanner e sua própria conexão com a API.

---

## Exemplo da aula (Alura)

```java
public class ExemploConstantes {
    public static final int ANO_ATUAL = 2022;
    public static final String NOME_EMPRESA = "Alura";
}

// acessando sem instanciar:
System.out.println(ExemploConstantes.ANO_ATUAL);    // 2022
System.out.println(ExemploConstantes.NOME_EMPRESA); // Alura
```

---

## Resumo

- `final` → valor não pode ser alterado
- `static` → pertence à classe, não ao objeto
- `static final` → constante de classe: uma única cópia na memória, acessível sem instanciar
