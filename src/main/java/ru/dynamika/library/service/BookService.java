package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;

public interface BookService {

    String getAllBooks();

    String saveNewBook(BookDto bookDTO);

    String updateBook(Book book);

}
