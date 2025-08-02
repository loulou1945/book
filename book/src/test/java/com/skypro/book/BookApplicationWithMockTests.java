package com.skypro.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.book.controller.BookController;
import com.skypro.book.model.Book;
import com.skypro.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(MockedBeansConfig.class)
public class BookApplicationWithMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createBookTest() throws Exception {

        String name = "123";
        String author = "qwe";
        long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setAuthor(name);
        book.setName(author);

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("id", id);
        bookMap.put("name", name);
        bookMap.put("author", author);

        String jsonRequest = objectMapper.writeValueAsString(bookMap);

        mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.author").value(author));
    }

    @Test
    public void getBookById() throws Exception {

        String name = "123";
        String author = "qwe";
        long id = 1;

        Book book = new Book();
        book.setId(id);
        book.setAuthor(name);
        book.setName(author);

        when(bookService.findBook(id)).thenReturn(book);

        mockMvc.perform(get("/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.author").value(author));

    }
}
