package ru.dynamika.library.store.service;

import ru.dynamika.library.api.dto.BookDto;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.api.request.BookUpdateRequestDto;
import ru.dynamika.library.api.response.BookResponse;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    BookResponse saveNewBook(BookDto bookDTO);

    BookResponse updateBook(BookUpdateRequestDto bookUpdateRequestDto);

}
