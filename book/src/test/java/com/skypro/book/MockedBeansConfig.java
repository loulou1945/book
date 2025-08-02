package com.skypro.book;

import com.skypro.book.service.BookService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockedBeansConfig {

    @Bean
    public BookService bookService() {
        return Mockito.mock(BookService.class);
    }
}
