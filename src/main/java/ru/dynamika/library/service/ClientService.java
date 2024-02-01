package ru.dynamika.library.service;

import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Client;


public interface ClientService {

    String getAllClients();

    String saveNewClient(ClientDto clientDTO);

    String updateClient(Client client);

    String getClientsWithRentedBooks();

    String addNewBookToClient(BookRentDto bookRentDto);
}
