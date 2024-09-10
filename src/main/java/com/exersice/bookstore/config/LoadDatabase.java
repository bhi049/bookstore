package com.exersice.bookstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;

@Configuration
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(BookRepository repository) {
    return args -> {
      repository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 10.99));
            repository.save(new Book("1984", "George Orwell", 1949, "9780451524935", 8.99));
    };
  }
}
