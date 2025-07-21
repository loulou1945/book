package com.skypro.book.repositories;

import com.skypro.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameIgnoreCase(String name);

    Collection<Book> findBooksByAuthorContainsIgnoreCase(String author);

    Collection<Book> findAllByNameContainsIgnoreCase(String name);

}
