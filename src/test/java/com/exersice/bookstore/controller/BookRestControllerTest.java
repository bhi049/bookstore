package com.exersice.bookstore.controller;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookRestControllerTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookRestController bookRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks_Success() {
        // Arrange
        Book book1 = new Book("Book One", "Author A", 2020, "123456", 12.99, null);
        Book book2 = new Book("Book Two", "Author B", 2021, "654321", 15.99, null);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        ResponseEntity<?> response = bookRestController.getAllBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllBooks_NoContent() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<?> response = bookRestController.getAllBooks();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetBookById_Success() {
        // Arrange
        Book book = new Book("Book One", "Author A", 2020, "123456", 12.99, null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<?> response = bookRestController.getBookById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testGetBookById_NotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = bookRestController.getBookById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
