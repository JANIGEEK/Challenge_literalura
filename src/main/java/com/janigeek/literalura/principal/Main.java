package com.janigeek.literalura.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.janigeek.literalura.models.Author;
import com.janigeek.literalura.models.AuthorData;
import com.janigeek.literalura.models.BookData;
import com.janigeek.literalura.models.Books;
import com.janigeek.literalura.models.Results;
import com.janigeek.literalura.repository.AuthorRepository;
import com.janigeek.literalura.repository.BooksRepository;
import com.janigeek.literalura.service.ConsumptionAPI;
import com.janigeek.literalura.service.ConvertData;

public class Main {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner keyboard = new Scanner(System.in);
    private ConsumptionAPI consumptionApi = new ConsumptionAPI();
    private ConvertData convertData = new ConvertData();
    private AuthorRepository authorRepository;
    private BooksRepository booksRepository;
    private List<Books> books;
    private List<Author> author;

    // crear constructor para setear los repositorios
    public Main(BooksRepository booksRepository2, AuthorRepository authorRepository2) {
        this.authorRepository = authorRepository2;
        this.booksRepository = booksRepository2;
    }

    public void showMenu() {
        int choose = -1;
        var menu = """
                -------------------
                SELECCIONE UNA ACCION A REALIZAR
                *******************
                1-Introducir libro
                2-Listar libros guardados
                3-Listar autores guardados
                4-Listar idiomas guardados
                5-Listar autores vivos de acuerdo al año
                0-Salir
                -------------------
                """;
        while (choose != 0) {
            System.out.println(menu);
            choose = keyboard.nextInt();
            keyboard.nextLine();

            switch (choose) {
                case 1:
                    EnterBookWeb();
                    break;
                case 2:
                    ListBooksWeb();
                    break;
                case 3:
                    ListAuthorsWeb();
                    break;
                case 4:
                    ListLenguagesWeb();
                    break;
                case 5:
                    ListLivingAuthorsWeb();
                    break;
                case 0:
                    System.out.println("Cerrrando aplicacion ....");

                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
        keyboard.close();

    }

    private void ListLivingAuthorsWeb() {
        System.out.println("Ingrese un año");
        var yearL = keyboard.nextInt();
        author = authorRepository.livingAuthorDuringYears(yearL);
        if (author == null) {
            System.out.println("No hay autores que coincidan con el año de busqueda");
        } else {
            author.forEach(System.out::println);
        }
    }

    private void ListLenguagesWeb() {
        int languageChoose = -1;
        var menuLanguage = """
                -------------------
                SELECCIONE UN IDIOMA
                *******************
                1-Ingles
                2-Español
                3-Portugues
                4-Italiano
                -------------------
                """;
        System.out.println(menuLanguage);
        languageChoose = keyboard.nextInt();
        keyboard.nextLine();

        switch (languageChoose) {
            case 1:
                searchLenguage("en");
                break;
            case 2:
                searchLenguage("es");
                break;
            case 3:
                searchLenguage("pt");
                break;
            case 4:
                searchLenguage("it");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    private void searchLenguage(String language) {
        books = booksRepository.findByLanguages(language);
        if (books == null) {
            System.out.println("No hay libros registrados con este idioma");
        } else {
            books.forEach(System.out::println);
        }
    }

    private void ListAuthorsWeb() {
        author = authorRepository.findAll();
        author.forEach(System.out::println);
    }

    private void ListBooksWeb() {
        books = booksRepository.findAll();
        books.forEach(System.out::println);
    }

    private void EnterBookWeb() {
        System.out.println("Escribe el nombre del libro que desea buscar");
        var nameSerie = keyboard.nextLine();
        var json = consumptionApi.obtainData(URL_BASE + "?search=" + nameSerie.replace(" ", "+"));
        // System.out.println(json);
        // almacena todos los resultados de la busqueda
        var searchEngine = convertData.obtainData(json, Results.class);

        // compara si el nombre buscado aparece en los resultados de la busqueda, y
        // encuentra el primero que se nombre igula
        Optional<BookData> searchBook = searchEngine.results().stream()
                .filter(b -> b.title().toUpperCase().contains(nameSerie.toUpperCase()))
                .findFirst();

        // analiza si existe una coincidencia en la busqueda
        if (searchBook.isPresent()) {
            BookData bookData = searchBook.get();
            AuthorData authorData = bookData.authors().get(0);
            Author author = authorRepository.findByNameAuthor(authorData.name());
            // analiza si ya existe un autor con ese nombre de lo contrario lo almacena
            if (author == null) {
                author = new Author(authorData);
                authorRepository.save(author);
            }
            // analiza si ya existe un libro con ese nombre de lo contrario lo almacena
            // obtener nombre del libro
            Books book = booksRepository.findByTitle(bookData.title());
            if (book == null) {
                book = new Books(bookData, author);
                booksRepository.save(book);
                System.out.println(book);
            } else {
                System.out.println("Ya se ha registrado este libro anteriormente");
            }
        } else {
            System.out.println("No se encontro el libro que busca");
        }
    }

}
