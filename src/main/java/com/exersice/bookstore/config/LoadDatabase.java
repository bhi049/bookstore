package com.exersice.bookstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.CategoryRepository;
import com.exersice.bookstore.model.User;
import com.exersice.bookstore.model.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository, CategoryRepository categoryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Category fiction = new Category("Fiction");
            Category nonFiction = new Category("Non-Fiction");

            categoryRepository.save(fiction);
            categoryRepository.save(nonFiction);

            String adminPassword = passwordEncoder.encode("admin");
            String userPassword = passwordEncoder.encode("password");

            userRepository.save(new User("admin", adminPassword, "admin@bookstore.com", "ROLE_ADMIN"));
            userRepository.save(new User("user", userPassword, "user@bookstore.com", "ROLE_USER"));

            Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, userPassword, 10.99, fiction);
            Book book2 = new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011, userPassword, 14.99, nonFiction);
            

            bookRepository.save(book1);
            bookRepository.save(book2);

        };
    }
}

