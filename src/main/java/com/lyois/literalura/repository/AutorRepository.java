package com.lyois.literalura.repository;

import com.lyois.literalura.models.autor.Autor;
import com.lyois.literalura.models.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Boolean existsByNome(String nome);

    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoDeMorte >= :anoSelecionado AND :anoSelecionado >= a.anoDeNascimento")
    List<Autor> buscarPorAnoDeFalecimento(int anoSelecionado);

    @Query("SELECT a FROM Autor a WHERE a.nome ILIKE %:nome%")
    List<Autor> encontrarAutorPeloNome(String nome);

    @Query("SELECT b FROM Livro b WHERE b.autor.nome ILIKE %:nome%")
    List<Livro> encontrarMediaDeDownloadsPorAutor(String nome);
}
