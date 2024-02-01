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
@RequestMapping("/api/v1/books")
public class LibraryController {

    @Autowired
    private final BookService bookService;


    @PostMapping("/")
    public String saveNewBook(@RequestBody BookDto bookDTO) {
        return bookService.saveNewBook(bookDTO);
    }

    @GetMapping("/")
    public String getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/")
    public String updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }
}
