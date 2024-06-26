package com.janigeek.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.janigeek.literalura.principal.Main;
import com.janigeek.literalura.repository.AuthorRepository;
import com.janigeek.literalura.repository.BooksRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private BooksRepository booksRepository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(booksRepository, authorRepository);
		main.showMenu();
	}

}
