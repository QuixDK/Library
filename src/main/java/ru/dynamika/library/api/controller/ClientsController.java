package ru.dynamika.library.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.api.dto.BookRentDto;
import ru.dynamika.library.api.dto.ClientDto;
import ru.dynamika.library.api.exceptions.BadRequestException;
import ru.dynamika.library.api.exceptions.NotFoundException;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.store.repository.ClientRepository;
import ru.dynamika.library.store.service.ClientService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Tag(name = "Clients")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientsController {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;

    @Operation (
            summary = "Create or update client",
            description = "Get ClientDto and create new client, then add it to database",
            method = "createOrUpdateClient"
    )
    @SneakyThrows
    @PutMapping("/")
    public ClientDto createOrUpdateClient(
            @RequestParam(name = "client_id", required = false) Optional<Integer> optionalClientId,
            @RequestParam(name = "client_name", required = false) Optional<String> optionalClientName,
            @RequestParam(name = "client_birthday", required = false) Optional<String> optionalClientBirthdate) {


        String pattern = "dd/MM/uuuu";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        optionalClientBirthdate = optionalClientBirthdate.filter(clientBirthday -> !clientBirthday.trim().isEmpty());
        optionalClientName = optionalClientName.filter(clientName -> !clientName.trim().isEmpty());

        boolean isCreate = !optionalClientId.isPresent();

        if (isCreate && !optionalClientName.isPresent() ){
            throw new BadRequestException("Client name can't be empty.");
        }
        if (isCreate && !optionalClientBirthdate.isPresent()) {
            throw new BadRequestException("Client birthday can't be empty.");
        }
        LocalDate localDate = LocalDate.parse(optionalClientBirthdate.get(), dateTimeFormatter);
        LocalDate nowDate = LocalDate.now(ZoneId.of("UTC"));
        if (localDate.isAfter(nowDate)) {
            throw new BadRequestException("Client birthday is uncorrected.");
        }

        final Client client = optionalClientId
                .map(this::getClientOrThrowException)
                .orElseGet(() -> Client.builder().build());

        optionalClientName
                .ifPresent(client::setFullName);

        client.setBirthday(localDate);

        final Client savedClient = clientRepository.saveAndFlush(client);

        return ClientDto.builder()
                .id(savedClient.getId())
                .fullName(savedClient.getFullName())
                .birthday(savedClient.getBirthday())
                .build();
    }

    public Client getClientOrThrowException(Integer clientId) {

        return clientRepository
                .findById(clientId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Client with \"%s\" doesn't exist.",
                                        clientId
                                )
                        )
                );
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

}
