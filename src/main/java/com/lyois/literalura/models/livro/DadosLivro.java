package com.lyois.literalura.models.livro;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lyois.literalura.models.autor.DadosAutor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("download_count") int quantidadeDownloads) {
}
