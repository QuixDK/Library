package ru.dynamika.library.service;

import ru.dynamika.library.DTO.BookDTO;
import ru.dynamika.library.model.Book;

public interface BookService {

    String getAllBooks();
    void saveNewBook(BookDTO bookDTO);

    String updateBook(Book book);

}
