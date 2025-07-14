package com.skypro.book.controller;

import com.skypro.book.model.Book;
import com.skypro.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}") // GET http://localhost:8080/books/1
    public ResponseEntity<Book> getBookInfo(@PathVariable Long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping // POST http://localhost:8080/books
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping //PUT http://localhost:8080/books
    public ResponseEntity<Book> editBook(@RequestBody Book book) {
        Book foundBook = bookService.editBook(book);
        if (foundBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundBook);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8080/books/1
    public Book deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}
