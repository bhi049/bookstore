package com.exersice.bookstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.CategoryRepository;

@Configuration
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(BookRepository bookrepository, CategoryRepository categoryrepository) {
    return args -> {
      Category fiction = new Category("Fiction");
      Category nonFiction = new Category("Non-Fiction");

      categoryrepository.save(fiction);
      categoryrepository.save(nonFiction);

      bookrepository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 10.99, nonFiction));
      bookrepository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 8.99, nonFiction));
    };
  }
}
