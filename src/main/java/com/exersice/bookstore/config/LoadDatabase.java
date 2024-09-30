package com.exersice.bookstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.CategoryRepository;
import com.exersice.bookstore.model.User;
import com.exersice.bookstore.model.UserRepository;

@Configuration
public class LoadDatabase {

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Bean for password encoding
  }

  @Bean
  CommandLineRunner initDatabase(BookRepository bookRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
    return args -> {
      // Create categories
      Category fiction = new Category("Fiction");
      Category nonFiction = new Category("Non-Fiction");

      categoryRepository.save(fiction);
      categoryRepository.save(nonFiction);

      // Add books with categories
      bookRepository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 10.99, fiction));
      bookRepository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 8.99, nonFiction));

      // Password encoder instance
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      // Add users to the database with encoded passwords
      userRepository.save(new User("admin", encoder.encode("admin"), "admin@bookstore.com", "ADMIN"));
      userRepository.save(new User("user", encoder.encode("password"), "user@bookstore.com", "USER"));
    };
  }
}
