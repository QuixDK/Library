package ru.dynamika.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.DTO.BookDTO;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.service.BookServiceImpl;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    @Autowired
    private final BookServiceImpl bookServiceImpl;


    @PostMapping("/api/v1/books")
    public void saveNewBook(@RequestBody BookDTO bookDTO) {
        bookServiceImpl.saveNewBook(bookDTO);
    }

    @GetMapping("/api/v1/books")
    public String getAllBooks() {
        return bookServiceImpl.getAllBooks();
    }

    @PutMapping("/api/v1/books")
    public String updateBook(@RequestBody Book book) {
        return bookServiceImpl.updateBook(book);
    }
}
