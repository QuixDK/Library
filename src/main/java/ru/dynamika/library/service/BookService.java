package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.request.BookUpdateRequestDto;
import ru.dynamika.library.response.BookResponse;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    BookResponse saveNewBook(BookDto bookDTO);

    BookResponse updateBook(BookUpdateRequestDto bookUpdateRequestDto);

}
