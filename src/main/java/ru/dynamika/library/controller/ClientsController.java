package ru.dynamika.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.DTO.ClientDTO;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.service.ClientServiceImpl;

@RestController
@RequiredArgsConstructor
public class ClientsController {

    @Autowired
    private final ClientServiceImpl clientService;

    @PostMapping("/api/v1/clients")
    public void addNewClient(@RequestBody ClientDTO clientDTO) {
        clientService.saveNewClient(clientDTO);
    }

    @GetMapping("/api/v1/clients")
    public String getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/api/v1/clients")
    public String updateBook(@RequestBody Client client) {
        return clientService.updateClient(client);
    }
}
