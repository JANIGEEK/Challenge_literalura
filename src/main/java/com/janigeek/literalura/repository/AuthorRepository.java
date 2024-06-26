package com.janigeek.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.janigeek.literalura.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNameAuthor(String nameString);

    @Query("SELECT a FROM Author a WHERE a.birthYear >= :year AND a.deathYear <= :year")
    List<Author> livingAuthorDuringYears(int year);
}
