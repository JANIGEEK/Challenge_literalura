package com.janigeek.literalura.models;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String birthYear;
    private String deathYear;
    // crear la opcion de un autor tiene muchos libros
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // ?
    private Set<Books> books = new HashSet<>();

    public Author() {
    }

    // setear los valores de las columnas con lo obtenido en la busqueda
    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birthYear = authorData.birthYear();
        this.deathYear = authorData.deathYear();
    }

    @Override
    public String toString() {
        return "Autor: " + name + "\n" +
                "Año de nacimiento: " + birthYear + "\n" +
                "Año de muerte: " + deathYear + "\n" +
                "Libros: " + (books != null ? books.stream()
                        .map(Books::getTitle)
                        .collect(Collectors.joining(", ")) : "N/A")
                + "\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(String deathYear) {
        this.deathYear = deathYear;
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }

}
