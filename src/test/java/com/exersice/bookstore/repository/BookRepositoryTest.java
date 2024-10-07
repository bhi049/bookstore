package com.exersice.bookstore.repository;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateBook() {
        // Arrange
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        Book book = new Book("Test Book", "Test Author", 2020, "123456789", 19.99, category);

        // Act
        Book savedBook = bookRepository.save(book);

        // Assert
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
    }

    @Test
    public void testFindBookById() {
        // Arrange
        Category category = new Category("Non-Fiction");
        categoryRepository.save(category);
        
        Book book = new Book("Sample Book", "Sample Author", 2021, "987654321", 15.99, category);
        Book savedBook = bookRepository.save(book);

        // Act
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals(savedBook.getTitle(), foundBook.get().getTitle());
    }

    @Test
    public void testDeleteBook() {
        // Arrange
        Category category = new Category("Fiction");
        categoryRepository.save(category);
        
        Book book = new Book("Sample Book", "Sample Author", 2021, "987654321", 15.99, category);
        Book savedBook = bookRepository.save(book);

        // Act
        bookRepository.deleteById(savedBook.getId());

        // Assert
        Optional<Book> deletedBook = bookRepository.findById(savedBook.getId());
        assertFalse(deletedBook.isPresent());
    }

    @Test
    public void testFindAllBooks() {
        // Arrange
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        Book book1 = new Book("Book One", "Author A", 2020, "111111", 12.99, category);
        Book book2 = new Book("Book Two", "Author B", 2021, "222222", 15.99, category);

        bookRepository.save(book1);
        bookRepository.save(book2);

        // Act
        List<Book> books = (List<Book>) bookRepository.findAll();

        // Assert
        assertEquals(2, books.size());
    }
}
