package ru.dynamika.library.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.dto.*;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.model.RentedBook;
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

    @PostMapping("/")
    public String addNewClient(@RequestBody ClientDto clientDTO) {
        return clientService.saveNewClient(clientDTO);
    }

    @PostMapping("/books/rent")
    public String addNewBookToClient(@RequestBody BookRentDto bookRentDto) {
        return clientService.addNewBookToClient(bookRentDto);
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

        List<ClientRentedBooksDto> result = new ArrayList<>();


        for (Client client : clients) {
            if (client.getRentedBooks().isEmpty()) {
                continue;
            }

            ClientRentedBooksDto clientRentedBooksDto = getClientRentedBooksDto(client);

            result.add(clientRentedBooksDto);
        }


        return objectMapper.writeValueAsString(result);
    }

    private static ClientRentedBooksDto getClientRentedBooksDto(Client client) {
        List<RentedBookDto> rentedBooksDtoList = new ArrayList<>();

        for (RentedBook rentedBook : client.getRentedBooks()) {
            BookDto bookDto = new BookDto(rentedBook.getBook().getName(), rentedBook.getBook().getAuthor(), rentedBook.getBook().getIsbn());

            RentedBookDto rentedBookDto = new RentedBookDto(bookDto, rentedBook.getRentalTimestamp());
            rentedBooksDtoList.add(rentedBookDto);
        }

        return new ClientRentedBooksDto(
                client.getId(),
                client.getFullName(),
                client.getBirthday(),
                rentedBooksDtoList
        );
    }

    @PutMapping("/")
    public String updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }


}
