package Exemplos.jackson;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Json {
    @JsonAlias({"nomeCompleto", "nome"})
    private String nomeCompleto;
}
