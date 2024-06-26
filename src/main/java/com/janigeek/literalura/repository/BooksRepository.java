package com.janigeek.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janigeek.literalura.models.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    Books findByTitle(String bookTitle);

    List<Books> findByLanguages(String language);
}
