package ru.dynamika.library.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.store.model.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByIsbn(String isbn);

    Optional<Book >findByIsbn(String isbn);
}
