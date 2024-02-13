package ru.dynamika.library.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.store.model.RentedBook;

@Repository
public interface RentedBookRepository extends JpaRepository<RentedBook, Integer> {
}
