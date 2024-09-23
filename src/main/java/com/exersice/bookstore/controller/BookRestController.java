package com.exersice.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/books")
public class BookRestController {

  private final BookRepository bookRepository;
  
  public BookRestController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping
  public List<Book> getAllBooks() {
    return (List<Book>) bookRepository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Book> getBookById(@PathVariable Long id) {
    return bookRepository.findById(id);
  }
}
  


