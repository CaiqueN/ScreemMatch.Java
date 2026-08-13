package br.com.alura.model;

import com.br.alura.model.DadosEpisodio;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporadas(@JsonAlias ("Season") Integer numero,
                              @JsonAlias("Episodes") List<DadosEpisodio> episodios ) {
}
