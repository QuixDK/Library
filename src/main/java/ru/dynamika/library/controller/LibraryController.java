package ru.dynamika.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.service.BookService;
import ru.dynamika.library.service.BookServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LibraryController {

    @Autowired
    private final BookService bookService;


    @PostMapping("/books")
    public String saveNewBook(@RequestBody BookDto bookDTO) {
        return bookService.saveNewBook(bookDTO);
    }

    @GetMapping("/books")
    public String getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/books")
    public String updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }
}
