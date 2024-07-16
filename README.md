![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)&nbsp;
![Postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)&nbsp;


# Challenge Literatura 2024

## Apresentação


Literalura é uma aplicação Java criada durante o curso da Alura para o programa Oracle ONE. Ela utiliza a API Gutendex para acessar o catálogo  
de livros do Project Gutenberg, uma biblioteca digital com obras gratuitas. Através de comandos no terminal, os usuários podem buscar livros  
e salvar seus títulos e autores em um banco de dados local.

## O Literalura permite:

* **Buscar livro pelo título**: Busque e salve livros de seu interesse no banco de dados.
* **Listar livros registrados**: Veja todos os livros registrados.
* **Listar autores registrados**: Lista todos os autores registrados.
* **Listar autores vivos em um determinado ano**: Descubra autores que estavam vivos em um ano específico.
* **Listar livros em determinado idioma**: Filtre os livros registrados por idioma.
* **Listar Top 10 livros mais baixados**: Veja o top 10 livros registrados mais baixados.
* **Buscar autor**: Busque informações sobre seus autores favoritos e salve seus livros.
* **Media de downloads por autor**: Veja a média de downloads por autor.

## Configuração POSTGRES

Defina as seguintes variáveis de ambiente:

* `DB_HOST`: Host do PostgreSQL (se estiver rodando localmente, será localhost)
* `DB_NAME`: Nome do banco de dados criado
* `DB_USER`: Seu usuário PostgreSQL
* `DB_PASSWORD`: Sua senha PostgreSQL

No arquivo de configuração, utilize as variáveis da seguinte maneira:

```
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```
