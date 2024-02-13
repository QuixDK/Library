package ru.dynamika.library.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.api.dto.BookDto;
import ru.dynamika.library.api.request.BookUpdateRequestDto;
import ru.dynamika.library.store.service.BookService;

@Tag(name = "Books")
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
