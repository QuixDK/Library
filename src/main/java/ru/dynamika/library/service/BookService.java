package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookDto;
import ru.dynamika.library.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    String saveNewBook(BookDto bookDTO);

    String updateBook(Book book);

}
