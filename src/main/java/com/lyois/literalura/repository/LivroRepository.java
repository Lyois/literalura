package com.lyois.literalura.repository;

import com.lyois.literalura.models.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByNome(String nome);

    Livro findByNome(String nome);

    @Query("SELECT DISTINCT b.idioma FROM Livro b ORDER BY b.idioma")
    List<String> buscarIdiomas();

    @Query("SELECT b FROM Livro b WHERE idioma = :idiomaSelecionado")
    List<Livro> buscarLivrosDoIdioma(String idiomaSelecionado);

    List<Livro> findTop10ByOrderByQuantidadeDeDownloadsDesc();


}
