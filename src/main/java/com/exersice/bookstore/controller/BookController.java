package com.exersice.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




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

    @GetMapping("/addbook")
    public String showAddBookForm(Model model) {
      model.addAttribute("book", new Book());
      return "addbook";
    }
    

    @PostMapping("/addbook")
    public String addBook(@ModelAttribute Book book) {
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
      return "editbook";  
  }

    @PostMapping("/booklist/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book) {
      bookRepository.save(book); 
      return "redirect:/booklist";  
}
    


  }
