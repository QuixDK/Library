package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.request.ClientUpdateRequestDto;
import ru.dynamika.library.response.ClientResponse;

import java.util.List;


public interface ClientService {

    List<Client> getAllClients();

    ClientResponse createClient(ClientDto clientDTO);

    ClientResponse updateClient(ClientUpdateRequestDto clientUpdateRequestDto);

    List<Client> getClientsWithRentedBooks();

    ClientResponse addNewBookToClient(BookRentDto bookRentDto);
}
