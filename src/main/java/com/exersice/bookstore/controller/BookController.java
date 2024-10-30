package com.exersice.bookstore.controller;

import com.exersice.bookstore.model.Book;
import com.exersice.bookstore.model.BookRepository;
import com.exersice.bookstore.model.Category;
import com.exersice.bookstore.model.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    // Show book list
    @GetMapping("/booklist")
    public String getBookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist"; // Matches booklist.html
    }

    // Show the form to add a new book (GET request)
    @GetMapping("/addbook")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook"; // Matches addbook.html
    }

    // Handle adding a new book (POST request)
    @PostMapping("/addbook")
    public String addBook(@ModelAttribute("book") Book book, @RequestParam("category") Long categoryId) {
        try {
            // Debugging: Print book and category information
            System.out.println("Book details: " + book);
            System.out.println("Category ID: " + categoryId);

            // Retrieve the category by ID
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category Id: " + categoryId));
            book.setCategory(category);
            // Save the new book
            bookRepository.save(book);
            return "redirect:/booklist";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Matches error.html
        }
    }

    // Show the edit form (GET request)
    @GetMapping("/booklist/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        try {
            // Retrieve the book by ID or throw an exception if not found
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
            model.addAttribute("book", book);
            model.addAttribute("categories", categoryRepository.findAll());
            return "editbook"; // Matches editbook.html
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Matches error.html
        }
    }

    // Handle form submission (POST request) for updating a book
    @PostMapping("/booklist/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @RequestParam("category") Long categoryId,
                             @ModelAttribute("book") Book updatedBook, Model model) {
        try {
            // Retrieve the existing book entity from the database
            Book existingBook = bookRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));

            // Update fields from the submitted form
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setIsbn(updatedBook.getIsbn());

            // Update the category based on the submitted category ID
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category Id: " + categoryId));
            existingBook.setCategory(category);

            // Save the updated book
            bookRepository.save(existingBook);

            return "redirect:/booklist";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while updating the book: " + e.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("book", updatedBook);
            return "editbook"; // Matches editbook.html
        }
    }

    // Delete a book
    @GetMapping("/booklist/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        try {
            // Retrieve the book by ID or throw an exception if not found
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
            // Delete the book
            bookRepository.delete(book);
            return "redirect:/booklist";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Matches error.html
        }
    }
}
