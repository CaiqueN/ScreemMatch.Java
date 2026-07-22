package Exemplos.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property {
    @JsonProperty("Nome")
    private String nomeCompleto;
}
