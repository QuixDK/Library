package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.request.ClientUpdateRequestDto;

import java.util.List;


public interface ClientService {

    List<Client> getAllClients();

    String createClient(ClientDto clientDTO);

    String updateClient(ClientUpdateRequestDto clientUpdateRequestDto);

    List<Client> getClientsWithRentedBooks();

    String addNewBookToClient(BookRentDto bookRentDto);
}
