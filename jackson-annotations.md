# Anotações Jackson: `@JsonProperty` vs `@JsonAlias`

## `@JsonProperty`

Utilizado para **leitura e escrita** do JSON.

Se ao final do código escrevermos:

```java
@JsonProperty("imdbVotes") String votos
```

Ao **gerar** (serializar) um JSON com dados sequenciais, o campo será incluído com o nome `"imdbVotes"`. Da mesma forma, ao **ler** (desserializar), buscará pelo nome `"imdbVotes"` no JSON.

---

## `@JsonAlias`

Utilizado **apenas para leitura** do JSON (desserialização).

```java
@JsonAlias("Title") String titulo
```

Nesse caso, o Jackson consegue **ler** o campo `"Title"` do JSON e mapeá-lo para o atributo `titulo`. Porém, ao **escrever** (serializar), utilizará o nome original do atributo Java — `titulo` — e não `"Title"`.

---

## Múltiplos aliases

Se a aplicação precisar buscar dados de APIs diferentes que utilizam nomes distintos para o mesmo campo, é possível passar um **array de nomes** no `@JsonAlias`:

```java
@JsonAlias({"Title", "Titulo"}) String titulo
```

Assim, o Jackson consegue desserializar o campo independentemente de a API retornar `"Title"` ou `"Titulo"`.

> **Exemplo prático:** suponha que, em vez do IMDb, utilizemos um serviço fictício chamado LMDb, que retorna o campo como `"Titulo"` em vez de `"Title"`. Com o array de aliases acima, ambos os formatos seriam aceitos sem alterar o restante do código.

---

## Paralelo com outras bibliotecas

> **Iasmin:** Podemos traçar um paralelo com o JSON (Android/Kotlin), que possui o `@SerializedName`. Assim, notamos que as bibliotecas são bastante semelhantes.

> **Jacqueline:** As bibliotecas possuem suas nuances. Tendo conhecimento sobre uma, acaba se tornando mais fácil trabalhar com a outra.

---

## Limpeza do código de demonstração

Após os exemplos, revertemos o código para manter apenas o `"Title"`:

1. Remover o alias `"Titulo"` do array, deixando apenas `@JsonAlias("Title")`.
2. Remover a linha de `votos` com `@JsonProperty("imdbVotes")`, utilizada apenas como demonstração.
3. Pressionar **`Ctrl + Alt + O`** para remover o import desnecessário de `@JsonProperty`.
