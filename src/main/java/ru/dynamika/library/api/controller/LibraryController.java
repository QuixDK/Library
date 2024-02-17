package ru.dynamika.library.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.api.dto.BookDto;
import ru.dynamika.library.api.dto.ClientDto;
import ru.dynamika.library.api.exceptions.BadRequestException;
import ru.dynamika.library.api.exceptions.NotFoundException;
import ru.dynamika.library.api.request.BookUpdateRequestDto;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.store.repository.BookRepository;
import ru.dynamika.library.store.service.BookService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Tag(name = "Books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class LibraryController {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    @Operation(
            summary = "Create or update Book",
            description = "Get id and book data to update existed book, else if id isn't found, save new book in db",
            method = "createOrUpdateBook"
    )
    @SneakyThrows
    @PutMapping("/")
    public ResponseEntity<BookDto> createOrUpdateBook(
            @RequestParam(name = "book_id", required = false) Optional<Integer> optionalBookId,
            @RequestParam(name = "book_name", required = false) Optional<String> optionalBookName,
            @RequestParam(name = "book_author", required = false) Optional<String> optionalBookAuthor,
            @RequestParam(name = "book_isbn", required = false) Optional<String> optionalBookIsbn) {


        optionalBookAuthor = optionalBookAuthor.filter(bookAuthor -> !bookAuthor.trim().isEmpty());
        optionalBookIsbn = optionalBookIsbn.filter(bookIsbn -> !bookIsbn.trim().isEmpty());
        optionalBookName = optionalBookName.filter(bookName -> !bookName.trim().isEmpty());

        boolean isCreate = !optionalBookId.isPresent();

        if (isCreate && !optionalBookName.isPresent()) {
            throw new BadRequestException("Book name can't be empty.");
        }
        if (isCreate && !optionalBookAuthor.isPresent()) {
            throw new BadRequestException("Book author can't be empty.");
        }
        if (isCreate && !optionalBookIsbn.isPresent()) {
            throw new BadRequestException("Book isbn can't be empty.");
        }

        if (bookRepository.findByIsbn(optionalBookIsbn.get()).isPresent()) {
            throw new BadRequestException(String.format("Book with isbn %s is already exist.", optionalBookIsbn.get()));
        }

        final Book book = optionalBookId
                .map(this::getBookOrThrowException)
                .orElseGet(() -> Book.builder().build());


        optionalBookName
                .ifPresent(book::setName);

        optionalBookIsbn
                .ifPresent(book::setIsbn);

        optionalBookAuthor
                .ifPresent(book::setAuthor);

        final Book savedBook = bookRepository.saveAndFlush(book);

        return ResponseEntity.ok(
                BookDto
                        .builder()
                        .id(savedBook.getId())
                        .name(savedBook.getName())
                        .author(savedBook.getAuthor())
                        .isbn(savedBook.getIsbn())
                        .build());
    }

    @SneakyThrows
    @GetMapping("/")
    public ResponseEntity<String> getAllBooks() {
        return ResponseEntity.ok(objectMapper.writeValueAsString(bookRepository.findAll()));
    }

    public Book getBookOrThrowException(Integer bookId) {

        return bookRepository
                .findById(bookId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Book with %s doesn't exist.",
                                        bookId
                                )
                        )
                );
    }

    @SneakyThrows
    @DeleteMapping("/")
    public ResponseEntity<String> deleteBook(
            @RequestParam(name = "book_id") Integer bookId) {
        Book book = getBookOrThrowException(bookId);
        bookRepository.delete(book);
        return ResponseEntity.ok(String.format("Book with id %s was deleted", bookId));
    }

}
