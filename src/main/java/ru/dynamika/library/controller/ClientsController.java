package ru.dynamika.library.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dynamika.library.DTO.ClientDTO;

@RestController
public class ClientsController {

    @PostMapping("/api/addClient")
    public void addNewClient(@RequestBody ClientDTO clientDTO) {

    }
}
