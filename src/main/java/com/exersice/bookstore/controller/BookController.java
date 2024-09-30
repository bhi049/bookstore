package com.exersice.bookstore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.CategoryRepository;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/booklist")
    public String showBookList(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("books", books);
        
        // Get the authenticated username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        model.addAttribute("username", currentUsername);
        
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
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
        book.setCategory(category);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    @GetMapping("/booklist/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }

    @PostMapping("/booklist/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book updatedBook, @RequestParam("categoryId") Long categoryId) {
        Book book = bookRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

        book.setCategory(category);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());
        book.setIsbn(updatedBook.getIsbn());
        book.setPrice(updatedBook.getPrice());
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // Restrict delete to only ADMIN role
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/booklist/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            // Check if the book exists before deletion
            Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
            bookRepository.deleteById(id);
        } catch (Exception e) {
            // Handle any errors that occur during deletion
            return "redirect:/booklist?error=true";
        }
        return "redirect:/booklist?success=true";
    }

    // Show login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
