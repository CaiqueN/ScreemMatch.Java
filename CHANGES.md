# O que foi adicionado ao projeto Screenmatch

## Visão geral

O projeto evoluiu de uma aplicação Spring Boot básica para um sistema capaz de **consumir uma API externa (OMDB)**, receber o JSON de resposta e **deserializar automaticamente** os dados em objetos Java.

---

## Novas classes e interfaces

### `ConsumerAPI` — `service/ConsumerAPI.java`

Responsável por fazer requisições HTTP para APIs externas.

- Usa o cliente HTTP nativo do Java (`java.net.http.HttpClient`)
- Recebe uma URL como parâmetro e retorna o corpo da resposta como `String` (JSON)
- Exemplo de uso no projeto: busca dados de séries na API OMDB

```java
var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=...");
```

---

### `IConverteDados` — `service/IConverteDados.java`

Interface genérica que define o contrato para conversão de JSON em objetos Java.

- Usa **Generics** (`<T>`) para funcionar com qualquer classe
- Garante que qualquer implementação saiba converter JSON → objeto

```java
<T> T ObterDados(String json, Class<T> classe);
```

---

### `ConverteDados` — `service/ConverteDados.java`

Implementação de `IConverteDados` usando a biblioteca **Jackson**.

- Usa `ObjectMapper` para deserializar o JSON
- Funciona com qualquer classe passada como parâmetro (via Generics)

```java
DadosSeries dados = conversor.ObterDados(json, DadosSeries.class);
```

---

### `DadosSeries` — `model/DadosSeries.java`

Record que representa os dados de uma série vindos da API OMDB.

- Usa `@JsonAlias` para mapear os nomes dos campos do JSON para os atributos do record
- Usa `@JsonIgnoreProperties(ignoreUnknown = true)` para ignorar campos do JSON que não estão mapeados

| Campo Java       | Campo no JSON   |
|------------------|-----------------|
| `titulo`         | `Titulo`        |
| `totalTemporadas`| `TotalSeaseons` |
| `avaliacao`      | `Avaliacao`     |

---

## Classes de exemplo (pasta `Exemplos/`)

Criadas para ilustrar o uso das anotações do Jackson:

| Arquivo          | Anotação usada   | O que demonstra                                              |
|------------------|------------------|--------------------------------------------------------------|
| `Json.java`      | `@JsonAlias`     | Aceitar múltiplos nomes de campo no JSON para um mesmo atributo |
| `Property.java`  | `@JsonProperty`  | Mapear um nome fixo do JSON para um atributo com nome diferente |

---

## Fluxo completo da aplicação

```
ScreenmatchApplication.run()
        │
        ▼
ConsumerAPI.obterDados(url)   →   chama a API OMDB e retorna JSON
        │
        ▼
ConverteDados.ObterDados(json, DadosSeries.class)   →   converte JSON em objeto
        │
        ▼
DadosSeries (record)   →   dados prontos para uso
```
