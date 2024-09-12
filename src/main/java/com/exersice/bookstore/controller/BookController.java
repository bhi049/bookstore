package com.exersice.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;


@Controller
public class BookController {

  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

    @GetMapping("/index")
    public String showIndex(Model model) {
        model.addAttribute("message", "Welcome to the Bookstore");
        return "index";
    }

    @GetMapping("/booklist")
    public String showBookList(Model model) {
      List<Book> books = (List<Book>) bookRepository.findAll();
      model.addAttribute("books", books);
      return "booklist";
    }
  }
