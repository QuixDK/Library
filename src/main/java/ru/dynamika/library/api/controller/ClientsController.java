package ru.dynamika.library.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.api.dto.BookRentDto;
import ru.dynamika.library.api.dto.ClientDto;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.api.request.ClientUpdateRequestDto;
import ru.dynamika.library.store.service.ClientService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Clients")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientsController {

    @Autowired
    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    @Operation (
            summary = "Create client",
            description = "Get ClientDto and create new client, then add it to database",
            method = "createClient"
    )
    @SneakyThrows
    @PostMapping("/")
    public String createClient(@Valid @RequestBody ClientDto clientDTO) {
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
