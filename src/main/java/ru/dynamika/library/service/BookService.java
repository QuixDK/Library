package ru.dynamika.library.service;

import ru.dynamika.library.DTO.BookDTO;

public interface BookService {

    String getAllBooks();
    void saveNewBook(BookDTO bookDTO);

}
