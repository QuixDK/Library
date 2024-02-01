package ru.dynamika.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.model.RentedBook;

@Repository
public interface RentedBookRepository extends JpaRepository<RentedBook, Integer> {
}
