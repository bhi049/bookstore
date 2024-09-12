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
      repository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 10.99));
      repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 8.99));
    };
  }
}
