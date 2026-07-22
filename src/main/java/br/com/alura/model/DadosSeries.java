package br.com.alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSeries(@JsonAlias ("Titulo") String titulo ,
                          @JsonAlias ("TotalSeaseons") Integer totalTemporadas,
                          @JsonAlias("Avaliacao") String avaliacao) {
}
