package ru.dynamika.library.api.controller.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dynamika.library.api.exceptions.NotFoundException;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.store.model.RentedBook;
import ru.dynamika.library.store.repository.ClientRepository;

@Component
@RequiredArgsConstructor
public class ClientsHelper {

    private final ClientRepository clientRepository;

    public Client getClientOrThrowException(Integer clientId) throws NotFoundException {

        return clientRepository
                .findById(clientId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Client with \"%s\" doesn't exist.",
                                        clientId
                                )
                        )
                );
    }

    public boolean isBookAlreadyRented(Client client, Book book) {
        for (RentedBook rentedBook : client.getRentedBooks()) {
            if (rentedBook.getBook().equals(book)) {
                return true;
            }
        }
        return false;
    }
}
