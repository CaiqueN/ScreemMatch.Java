# Generics em Java

Generics permitem criar classes, interfaces e métodos que trabalham com **tipos desconhecidos ou parâmetros genéricos**. Eles fornecem uma forma de escrever código flexível e reutilizável, tornando-o independente de tipos específicos e permitindo que funcione com diferentes tipos de dados.

> Para criar um método ou classe genérico, usamos parâmetros de tipo representados entre `< >`. Por convenção, utilizamos letras maiúsculas únicas como `T`, `E`, `K`, `V`.

---

## Pacote: `caixa`

> Tudo relacionado à classe `Caixa` e seus exemplos está agrupado aqui.

### `Caixa.java` — Classe Genérica

```java
public class Caixa<T> {
    private T conteudo;

    public T getConteudo() {
        return conteudo;
    }

    public void setConteudo(T conteudo) {
        this.conteudo = conteudo;
    }
}
```

**Como funciona:**
- `<T>` é o **parâmetro de tipo** — um "curinga" que será substituído por um tipo real ao criar o objeto.
- `private T conteudo` armazena qualquer tipo de valor.
- O compilador garante a **segurança de tipos**: se você criar uma `Caixa<String>`, apenas `String` pode ser armazenada.

---

### `TestaCaixa.java` — Usando a classe genérica com tipos diferentes

```java
public class TestaCaixa {
    public static void main(String[] args) {
        Caixa<String> caixaDeTexto = new Caixa<>();
        caixaDeTexto.setConteudo("Guardando texto na minha caixa!");

        Caixa<Integer> caixaDeIdade = new Caixa<>();
        caixaDeIdade.setConteudo(30);

        Caixa<Double> caixaDeValor = new Caixa<>();
        caixaDeValor.setConteudo(150.50);
    }
}
```

**O que acontece em cada linha:**

| Variável        | Tipo genérico | Aceita apenas |
|-----------------|---------------|---------------|
| `caixaDeTexto`  | `String`      | `String`      |
| `caixaDeIdade`  | `Integer`     | `Integer`     |
| `caixaDeValor`  | `Double`      | `Double`      |

> O compilador **rejeita** qualquer tentativa de colocar um tipo errado. Ex: tentar fazer `caixaDeIdade.setConteudo("texto")` gera erro de compilação.

---

### `Caixa.java` — Método Genérico: `somaConteudoNaCaixa`

```java
public <T> T somaConteudoNaCaixa(T valor) {
    if (this.conteudo instanceof Integer c && valor instanceof Integer i) {
        Integer resultado = c + i;
        return (T) resultado;

    } else if (this.conteudo instanceof Double c && valor instanceof Double d) {
        Double resultado = c + d;
        return (T) resultado;

    } else if (this.conteudo instanceof String c && valor instanceof String s) {
        String resultado = c + "\n" + s;
        return (T) resultado;
    }

    return null;
}
```

**Passo a passo:**

1. **Assinatura genérica** — `public <T> T somaConteudoNaCaixa(T valor)`:
   - `<T>` declara que o método é genérico.
   - Recebe um parâmetro `valor` do tipo `T`.
   - Retorna um valor do mesmo tipo `T`.

2. **Pattern matching com `instanceof`** (disponível a partir do Java 16):
   - `this.conteudo instanceof Integer c` — verifica se o conteúdo é `Integer` **e** cria a variável `c` já com o tipo correto, sem necessidade de cast manual.
   - Funciona da mesma forma para `Double` e `String`.

3. **Comportamento por tipo:**

| Tipo      | Operação               | Exemplo                              |
|-----------|------------------------|--------------------------------------|
| `Integer` | Soma numérica          | `30 + 26 = 56`                       |
| `Double`  | Soma numérica          | `150.50 + 350.50 = 501.0`            |
| `String`  | Concatenação com quebra| `"linha1" + "\n" + "linha2"`         |
| Tipos diferentes | Retorna `null` | `caixaDeValor.somaConteudoNaCaixa("texto")` → `null` |

---

### `TestaCaixa.java` — Testando o método `somaConteudoNaCaixa`

```java
public static void main(String[] args) {
    Caixa<String> caixaDeTexto = new Caixa<>();
    caixaDeTexto.setConteudo("Guardando texto na minha caixa!");
    System.out.println(caixaDeTexto.somaConteudoNaCaixa("Mais uma linha"));

    Caixa<Integer> caixaDeIdade = new Caixa<>();
    caixaDeIdade.setConteudo(30);
    System.out.println(caixaDeIdade.somaConteudoNaCaixa(26));

    Caixa<Double> caixaDeValor = new Caixa<>();
    caixaDeValor.setConteudo(150.50);
    System.out.println(caixaDeValor.somaConteudoNaCaixa(350.50));
    System.out.println(caixaDeValor.somaConteudoNaCaixa("texto")); // tipos diferentes → null
}
```

**Saída esperada no terminal:**

```
Guardando texto na minha caixa!
Mais uma linha
56
501.0
null
```

---

## Resumo Visual

```
Generics em Java
│
├── Classe Genérica
│   └── Caixa<T>
│       ├── setConteudo(T valor)
│       └── getConteudo() → T
│
├── Instâncias Tipadas
│   ├── Caixa<String>   → aceita apenas String
│   ├── Caixa<Integer>  → aceita apenas Integer
│   └── Caixa<Double>   → aceita apenas Double
│
└── Método Genérico
    └── somaConteudoNaCaixa(T valor) → T
        ├── Integer + Integer → soma numérica
        ├── Double + Double   → soma numérica
        ├── String + String   → concatenação
        └── Tipos diferentes  → null
```

---

## Pontos-chave para memorizar

- `<T>` na classe → **todos os métodos** da classe podem usar `T`.
- `<T>` no método → o método define seu **próprio** tipo genérico, independente da classe.
- O compilador substitui `T` pelo tipo real **em tempo de compilação** (type erasure garante compatibilidade com código legado).
- `instanceof` com pattern variables (Java 16+) elimina a necessidade de cast manual após a verificação de tipo.
