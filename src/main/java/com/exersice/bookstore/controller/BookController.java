package com.exersice.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.CategoryRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;

  public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
    this.bookRepository = bookRepository;
    this.categoryRepository = categoryRepository;
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

    @GetMapping("/addbook")
    public String showAddBookForm(Model model) {
      model.addAttribute("book", new Book());
      model.addAttribute("categories", categoryRepository.findAll());
      return "addbook";
    }
    

    @PostMapping("/addbook")
    public String addBook(@ModelAttribute Book book, @RequestParam("categoryId") Long categoryId) {
      // Fetch the Category by ID
      Category category = categoryRepository.findById(categoryId)
              .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
      // Set the category to the book
      book.setCategory(category);
      // Save the book with the category set
      bookRepository.save(book);
      return "redirect:/booklist";
    }
    
    @GetMapping("/booklist/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    @GetMapping("/booklist/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID:" + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());  
        return "editbook";  
    }

    @PostMapping("/booklist/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book updatedBook, @RequestParam("categoryId") Long categoryId) {
      Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID:" + id));
      
      Category category = categoryRepository.findById(categoryId)
              .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
      book.setCategory(category);

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());
        book.setIsbn(updatedBook.getIsbn());
        book.setPrice(updatedBook.getPrice());
        book.setCategory(updatedBook.getCategory());

        bookRepository.save(book); 
        return "redirect:/booklist";
    }
 }
