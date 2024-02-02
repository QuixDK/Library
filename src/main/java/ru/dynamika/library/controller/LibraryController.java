package ru.dynamika.library.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.request.BookUpdateRequestDto;
import ru.dynamika.library.service.BookService;
import ru.dynamika.library.service.BookServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class LibraryController {

    @Autowired
    private final BookService bookService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @PostMapping("/")
    public String saveNewBook(@RequestBody BookDto bookDTO) {
        return objectMapper.writeValueAsString(bookService.saveNewBook(bookDTO));
    }

    @SneakyThrows
    @GetMapping("/")
    public String getAllBooks() {
        return objectMapper.writeValueAsString(bookService.getAllBooks());
    }

    @SneakyThrows
    @PutMapping("/")
    public String updateBook(@RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        return objectMapper.writeValueAsString(bookService.updateBook(bookUpdateRequestDto));
    }
}
