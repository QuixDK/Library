package ru.dynamika.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.repository.BookRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String getAllBooks() {
        return objectMapper.writeValueAsString(bookRepository.findAll());
    }

    @Override
    public String saveNewBook(BookDto bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            return "This book is already saved";
        }

        log.info("Save new book: " + bookRepository.save(Book.builder()
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .build()
        ));
        return "Save new book";
    }

    @Override
    public String updateBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            return "No such book";
        }
        log.info("Update book: " + bookRepository.save(book));
        return "Update book: " + bookRepository.findByIsbn(book.getIsbn());
    }

}
