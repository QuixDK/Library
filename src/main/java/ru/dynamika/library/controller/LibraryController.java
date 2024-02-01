package ru.dynamika.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.DTO.BookDTO;
import ru.dynamika.library.service.BookServiceImpl;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    @Autowired
    private final BookServiceImpl bookServiceImpl;


    @PostMapping("/api/saveBook")
    public void saveNewBook(@RequestBody BookDTO bookDTO) {
        bookServiceImpl.saveNewBook(bookDTO);
    }

    @GetMapping("/api/getBooks")
    public String getAllBooks() {
        return bookServiceImpl.getAllBooks();
    }

    @PutMapping("/api/updateBook")
    public void updateBook(@RequestBody BookDTO bookDTO) {

    }
}
