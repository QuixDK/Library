package ru.dynamika.library.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.library.store.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
