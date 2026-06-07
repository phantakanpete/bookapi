package com.phantakan.bookapi.controllers;

import com.phantakan.bookapi.entity.Book;
import com.phantakan.bookapi.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllBooks() throws Exception {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublishedDate(LocalDate.of(1990, 1, 1));

        bookRepository.save(book);

        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Book"));
    }

    @Test
    public void shouldReturnBooksByAuthor() throws Exception {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublishedDate(LocalDate.of(1990, 1, 1));

        bookRepository.save(book);

        mockMvc.perform(get("/books?author=test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].author").value("Test Author"));
    }

    @Test
    public void shouldReturn404WhenAuthorNotFound() throws Exception {
        mockMvc.perform(get("/books?author=test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateBooks() throws Exception {
        String requestBody = """
                    [
                        {
                            "title": "Test Book",
                            "author": "Test Author",
                            "publishedDate": "2533-01-01"
                        }
                    ]
                """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        List<Book> saved = bookRepository.findAll();

        assertEquals(1, saved.size());
        assertEquals("Test Book", saved.get(0).getTitle());
    }

    @Test
    public void shouldConvertBuddhistDateBeforeSave() throws Exception {
        String requestBody = """
                    [
                        {
                            "title": "Test Book",
                            "author": "Test Author",
                            "publishedDate": "2533-01-01"
                        }
                    ]
                """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        Book saved = bookRepository.findAll().get(0);

        assertEquals(LocalDate.of(1990, 1, 1), saved.getPublishedDate());
    }
}
