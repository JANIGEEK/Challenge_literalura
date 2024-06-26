package com.janigeek.literalura.models;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String nameAuthor;
    private Double downloads;
    // listar idiomas
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> languages;
    // muchos libros para un autor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    public Books() {
    }

    // setear los valores de la base de datos
    public Books(BookData bookData, Author author) {
        this.title = bookData.title();
        this.languages = bookData.languages();
        this.downloads = bookData.downloadsTotal();
        this.author = author;
        this.nameAuthor = bookData.authors().stream()
                .map(AuthorData::name).collect(Collectors.toList()).toString();
    }

    @Override
    public String toString() {
        return "------------DATOS DEL LIBRO-----------\n" +
                "Titulo: " + title + '\n' +
                "Autor: " + nameAuthor + '\n' +
                "Idiomas: " + languages + '\n' +
                "Descargas: " + downloads + '\n' +
                "----------------------------" + '\n';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
