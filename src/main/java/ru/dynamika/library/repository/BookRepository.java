package ru.dynamika.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByIsbn(String isbn);

    Book findByIsbn(String isbn);
}
