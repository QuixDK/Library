package ru.dynamika.library.service;

import ru.dynamika.library.DTO.BookDTO;
import ru.dynamika.library.DTO.ClientDTO;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.model.Client;


public interface ClientService {

    String getAllClients();
    void saveNewClient(ClientDTO clientDTO);
    String updateClient(Client client);
}
