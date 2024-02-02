package ru.dynamika.library.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.dto.*;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.request.ClientUpdateRequestDto;
import ru.dynamika.library.response.ClientResponse;
import ru.dynamika.library.service.ClientService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientsController {

    @Autowired
    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @PostMapping("/")
    public String createClient(@RequestBody ClientDto clientDTO) {
        return objectMapper.writeValueAsString(clientService.createClient(clientDTO));
    }

    @SneakyThrows
    @PostMapping("/books/rent")
    public String addNewBookToClient(@RequestBody BookRentDto bookRentDto) {
        return objectMapper.writeValueAsString(clientService.addNewBookToClient(bookRentDto));
    }

    @SneakyThrows
    @GetMapping("/")
    public String getAllClients() {
        return objectMapper.writeValueAsString(clientService.getAllClients());
    }

    @SneakyThrows
    @GetMapping("/rented-books")
    public String getClientsWithRentedBooks() {
        List<Client> clients = clientService.getClientsWithRentedBooks();

        if (clients.isEmpty()) {
            return "There is no clients";
        }
        List<Client> clientsWithBooks = new ArrayList<>();
        for (Client client: clients) {
            if (!client.getRentedBooks().isEmpty()) {
                clientsWithBooks.add(client);
            }
        }

        return objectMapper.writeValueAsString(clientsWithBooks);
    }

    @SneakyThrows
    @PutMapping("/")
    public String updateClient(@RequestBody ClientUpdateRequestDto clientUpdateRequestDto) {
        return objectMapper.writeValueAsString(clientService.updateClient(clientUpdateRequestDto));
    }


}
