package com.lyois.literalura.models.autor;

import com.lyois.literalura.models.livro.Livro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private int anoDeNascimento;
    private int anoDeMorte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoDeNascimento = dadosAutor.anoDeNascimento() != null ? dadosAutor.anoDeNascimento() : 0;
        this.anoDeMorte = dadosAutor.anoDeMorte() != null ? dadosAutor.anoDeMorte() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(int anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public int getAnoDeMorte() {
        return anoDeMorte;
    }

    public void setAnoDeMorte(int anoDeMorte) {
        this.anoDeMorte = anoDeMorte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "\n-------------------------------------------------" +
                "\nNome: " + nome +
                "\nData De Nascimento: " + anoDeNascimento +
                "\nData De Falecimento: " + anoDeMorte;
    }
}
