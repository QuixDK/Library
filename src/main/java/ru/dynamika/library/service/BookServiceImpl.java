package ru.dynamika.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.DTO.BookDTO;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.repository.BookRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    @Autowired
    private final BookRepo bookRepo;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String getAllBooks() {
        return objectMapper.writeValueAsString(bookRepo.findAll());
    }

    public void saveNewBook(BookDTO bookDTO) {
        log.info("Save new book: " + bookRepo.save(Book.builder()
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .ISBN(bookDTO.getISBN())
                .build()
        ));
    }

}
