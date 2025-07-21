package com.skypro.book;

import com.skypro.book.controller.BookController;
import com.skypro.book.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private BookController bookController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() throws Exception {
		Assertions.assertThat(bookController).isNotNull();
	}

	@Test
	public void testDefaultMessage() throws Exception {
		Assertions
				.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
				.isEqualTo("WebApp is working!");
	}

	@Test
	public void testGetBooks() throws Exception {
		Assertions
				.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/books", String.class))
				.isNotNull();
	}

	@Test
	public void testPostBooks() throws Exception {
		Book book = new Book();
		book.setName("Война и мир");
		book.setAuthor("Л.Н. Толстой");

		Assertions
				.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/books", book, String.class))
				.isNotNull();
	}
}
