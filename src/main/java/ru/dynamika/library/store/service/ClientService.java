package ru.dynamika.library.store.service;

import ru.dynamika.library.api.dto.BookRentDto;
import ru.dynamika.library.api.dto.ClientDto;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.api.request.ClientUpdateRequestDto;
import ru.dynamika.library.api.response.ClientResponse;

import java.util.List;


public interface ClientService {

    List<Client> getAllClients();

    ClientResponse createClient(ClientDto clientDTO);

    ClientResponse updateClient(ClientUpdateRequestDto clientUpdateRequestDto);

    List<Client> getClientsWithRentedBooks();

    ClientResponse addNewBookToClient(BookRentDto bookRentDto);
}
