package ru.dynamika.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
