package com.lyois.literalura.principal;

import com.lyois.literalura.models.autor.Autor;
import com.lyois.literalura.models.autor.DadosAutor;
import com.lyois.literalura.models.livro.DadosLivro;
import com.lyois.literalura.models.livro.Livro;
import com.lyois.literalura.repository.AutorRepository;
import com.lyois.literalura.repository.LivroRepository;
import com.lyois.literalura.service.ConsumoAPI;
import com.lyois.literalura.service.ConverteDados;

import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERCO = "https://gutendex.com/books";
    private AutorRepository autorRepository;
    private LivroRepository livroRepository;

    public Principal(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void exibirMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                                        
                    -------------------------------------------------
                    1 - BUSCAR LIVRO PELO TITULO
                    2 - LISTAR LIVROS REGISTRADOS
                    3 - LISTAR AUTORES REGISTRADOS
                    4 - LISTAR AUTORES VIVOS EM DETERMINADO ANO
                    5 - LISTAR LIVROS EM UM DETERMINADO IDIOMA
                    6 - TOP 10 LIVROS
                    7 - BUSCAR AUTORES POR NOME
                    8 - MÉDIA DE DOWNLOADS POR AUTOR
                                        
                    0 - S A I R
                    -------------------------------------------------
                    """;

            System.out.print(menu);
            option = leitura.nextInt();
            leitura.nextLine();

            switch (option) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarTodosOsLivros();
                    break;
                case 3:
                    listarTodosOsAutores();
                    break;
                case 4:
                    listarAutoresVivosPeloAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    listarTop10();
                    break;
                case 7:
                    buscarAutorPeloNome();
                    break;
                case 8:
                    mediaDeDownloadsPorAutor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivro() {
        System.out.println("\nDigite o nome do livro para busca");
        var nomeLivro = leitura.nextLine();
        var endercoBusca = ENDERCO + "/?search=";
        var dados = consumoApi.obterDados(endercoBusca + nomeLivro.replace(" ", "%20"));
        salvar(dados);
    }

    private void salvar(String dados) {
        try {
            Livro livro = new Livro(conversor.obterDados(dados, DadosLivro.class));
            Autor autor = new Autor(conversor.obterDados(dados, DadosAutor.class));
            Livro livroDb = null;
            Autor autorDb = null;
            if (!autorRepository.existsByNome(autor.getNome())) {
                autorRepository.save(autor);
                autorDb = autor;
            } else {
                autorDb = autorRepository.findByNome(autor.getNome());
            }

            if (!livroRepository.existsByNome(livro.getNome())) {
                livro.setAutor(autorDb);
                livroRepository.save(livro);
                livroDb = livro;
            } else {
                livroDb = livroRepository.findByNome(livro.getNome());
            }
            System.out.println(livroDb);
        } catch (NullPointerException e) {
            System.out.println("\n\n-------------Livro não encontrado!!!-------------\n");
        }

    }

    private void listarTodosOsLivros() {
        var buscarLivrosNoDb = livroRepository.findAll();
        if (!buscarLivrosNoDb.isEmpty()) {
            System.out.println("\nLivros Encontrados");
            buscarLivrosNoDb.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum livro encontrado no banco de dados!");
        }
    }

    private void listarTodosOsAutores() {
        var buscarAutoresNoDb = autorRepository.findAll();
        if (!buscarAutoresNoDb.isEmpty()) {
            System.out.println("\nAutores Encontrados");
            buscarAutoresNoDb.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontado no banco de dados!");
        }
    }

    private void listarAutoresVivosPeloAno() {
        System.out.println("\nInfome o ano para buscar o autor");
        var escolhaDoAno = leitura.nextInt();
        leitura.nextLine();
        var buscarAutorNoDb = autorRepository.buscarPorAnoDeFalecimento(escolhaDoAno);
        if (!buscarAutorNoDb.isEmpty()) {
            System.out.println("\nAutores vivos no ano de " + escolhaDoAno);
            buscarAutorNoDb.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado para está data!!");
        }
    }

    private void listarLivrosPorIdioma() {
        var idiomasCadastrados = livroRepository.buscarIdiomas();
        System.out.println("\nIdomas encontados no anco de dados");
        idiomasCadastrados.forEach(System.out::println);
        System.out.println("\nEscolha o idioma dos livros para procurar:");
        var idomaSelecionado = leitura.nextLine();
        livroRepository.buscarLivrosDoIdioma(idomaSelecionado).forEach(System.out::println);
    }

    private void listarTop10() {
        var top10 = livroRepository.findTop10ByOrderByQuantidadeDeDownloadsDesc();
        System.out.println("\nTOP 10");
        top10.forEach(System.out::println);
    }

    private void buscarAutorPeloNome() {
        System.out.println("\nInforme o nome do autor que deseja buscar");
        var nome = leitura.nextLine();
        var autor = autorRepository.encontrarAutorPeloNome(nome);
        if (!autor.isEmpty()) {
            autor.forEach(System.out::println);
        } else {
            System.out.println("\nAutor não encontrado!!!");
        }
    }

    private void mediaDeDownloadsPorAutor() {
        System.out.println("\nInforme o nome do autor que deseja buscar");
        var nome = leitura.nextLine();
        var autor = autorRepository.encontrarMediaDeDownloadsPorAutor(nome);
        if (!autor.isEmpty()) {
            DoubleSummaryStatistics media = autor.stream()
                    .mapToDouble(Livro::getQuantidadeDeDownloads)
                    .summaryStatistics();
            System.out.println("\nMédia de Downloads: " + media.getAverage());
        } else {
            System.out.println("\nAutor não encontrado!!!");
        }

    }

}