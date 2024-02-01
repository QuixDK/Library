package ru.dynamika.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.model.RentedBooks;

@Repository
public interface RentedBooksRepo extends JpaRepository<RentedBooks, Integer> {
}
