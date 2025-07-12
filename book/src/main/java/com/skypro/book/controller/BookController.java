package com.skypro.book.controller;

import com.skypro.book.model.Book;
import com.skypro.book.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}") // http://localhost:8080/books/1
    public Book getBookInfo (@PathVariable Long id) {
        return bookService.findBook(id);
    }

    @PostMapping //http://localhost:8080/books
    public Book createBook(Book book) {
        return bookService.createBook(book);
    }

//    @PutMapping
//
}
