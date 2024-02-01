package ru.dynamika.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.service.ClientService;
import ru.dynamika.library.service.ClientServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClientsController {

    @Autowired
    private final ClientService clientService;

    @PostMapping("/clients")
    public String addNewClient(@RequestBody ClientDto clientDTO) {
        return clientService.saveNewClient(clientDTO);
    }

    @PostMapping("/books/rent")
    public String addNewBookToClient(@RequestBody BookRentDto bookRentDto) {
        return clientService.addNewBookToClient(bookRentDto);
    }

    @GetMapping("/clients")
    public String getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/readingClients")
    public String getAllReadingClients() {
        return clientService.getAllReadingClients();
    }

    @PutMapping("/clients")
    public String updateBook(@RequestBody Client client) {
        return clientService.updateClient(client);
    }


}
