package ru.dynamika.library.store.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.api.dto.BookDto;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.store.repository.BookRepository;
import ru.dynamika.library.api.request.BookUpdateRequestDto;
import ru.dynamika.library.api.response.BookResponse;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;


    @SneakyThrows
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookResponse saveNewBook(BookDto bookDTO) {
        BookResponse bookResponse = new BookResponse();
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            bookResponse.setBook(null);
            bookResponse.setMessage("This book is already saved");
            return bookResponse;
        }
        Book book = Book.builder()
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .build();
        log.info("Save new book: " + bookRepository.save(book));
        bookResponse.setBook(book);
        bookResponse.setMessage("Save new book");
        return bookResponse;
    }

    @Override
    public BookResponse updateBook(BookUpdateRequestDto bookUpdateRequestDto) {
        BookResponse bookResponse = new BookResponse();
        if (!bookRepository.existsById(bookUpdateRequestDto.getId())) {
            bookResponse.setBook(null);
            bookResponse.setMessage("No such book");
            return bookResponse;
        }
        Book updatedBook = bookRepository.findById(bookUpdateRequestDto.getId()).get();

        if (bookRepository.existsByIsbn(bookUpdateRequestDto.getIsbn()) &&
                !bookRepository.findByIsbn(bookUpdateRequestDto.getIsbn()).equals(updatedBook)) {
            bookResponse.setBook(null);
            bookResponse.setMessage("Book with this isbn is already exist");
            return bookResponse;
        }
        updatedBook.setIsbn(bookUpdateRequestDto.getIsbn());
        updatedBook.setAuthor(bookUpdateRequestDto.getAuthor());
        updatedBook.setName(bookUpdateRequestDto.getName());

        log.info("Update book: " + bookRepository.save(updatedBook));
        bookResponse.setBook(updatedBook);
        bookResponse.setMessage("Book was updated!");
        return bookResponse;
    }

}
