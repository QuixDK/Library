package ru.dynamika.library.api.controller.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dynamika.library.api.exceptions.NotFoundException;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.store.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class LibraryHelper {

    private final BookRepository bookRepository;

    public Book getBookOrThrowException(Integer bookId) throws NotFoundException {

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
}
