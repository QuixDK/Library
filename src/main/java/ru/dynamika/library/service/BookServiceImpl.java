package ru.dynamika.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.repository.BookRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepo bookRepo;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String getAllBooks() {
        return objectMapper.writeValueAsString(bookRepo.findAll());
    }

    @Override
    public String saveNewBook(BookDto bookDTO) {
        if (bookRepo.existsByIsbn(bookDTO.getIsbn())) {
            return "This book is already saved";
        }

        log.info("Save new book: " + bookRepo.save(Book.builder()
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .build()
        ));
        return "Save new book";
    }

    @Override
    public String updateBook(Book book) {
        if (bookRepo.existsByIsbn(book.getIsbn())) {
            return "No such book";
        }
        log.info("Update book: " + bookRepo.save(book));
        return "Update book: " + bookRepo.findByIsbn(book.getIsbn());
    }

}
