package ru.dynamika.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.service.BookService;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibraryControllerTests {

    static final String END_POINT_PATH = "/api/v1/books/";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BookService bookService;

    @Test
    @SneakyThrows
    public void testCreateBookShouldReturn200Request() {
        BookDto book = new BookDto();
        book.setName("New test Book");
        book.setIsbn("randomIsbn");
        book.setAuthor("Tester");
        String requestBody = objectMapper.writeValueAsString(book);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(200))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
