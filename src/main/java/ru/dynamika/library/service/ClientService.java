package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Client;

import java.util.List;


public interface ClientService {

    List<Client> getAllClients();

    String saveNewClient(ClientDto clientDTO);

    String updateClient(Client client);

    List<Client> getClientsWithRentedBooks();

    String addNewBookToClient(BookRentDto bookRentDto);
}
