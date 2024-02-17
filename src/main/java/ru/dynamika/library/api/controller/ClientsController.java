package ru.dynamika.library.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dynamika.library.api.dto.BookRentDto;
import ru.dynamika.library.api.dto.ClientDto;
import ru.dynamika.library.api.exceptions.BadRequestException;
import ru.dynamika.library.api.exceptions.NotFoundException;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.store.model.RentedBook;
import ru.dynamika.library.store.repository.BookRepository;
import ru.dynamika.library.store.repository.ClientRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Tag(name = "Clients")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientsController {

    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;

    @Operation(
            summary = "Create or update client",
            description = "Get id and user data to update existed user, else if id isn't found, save new client in db",
            method = "createOrUpdateClient"
    )
    @SneakyThrows
    @PutMapping("/")
    public ResponseEntity<ClientDto> createOrUpdateClient(
            @RequestParam(name = "client_id", required = false) Optional<Integer> optionalClientId,
            @RequestParam(name = "client_name", required = false) Optional<String> optionalClientName,
            @RequestParam(name = "client_birthday", required = false) Optional<String> optionalClientBirthdate) {


        String pattern = "dd/MM/uuuu";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        optionalClientBirthdate = optionalClientBirthdate.filter(clientBirthday -> !clientBirthday.trim().isEmpty());
        optionalClientName = optionalClientName.filter(clientName -> !clientName.trim().isEmpty());

        boolean isCreate = !optionalClientId.isPresent();

        if (isCreate && !optionalClientName.isPresent()) {
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

        return ResponseEntity.ok(
                ClientDto
                        .builder()
                        .id(savedClient.getId())
                        .fullName(savedClient.getFullName())
                        .birthday(savedClient.getBirthday())
                        .build());
    }



    @SneakyThrows
    @PostMapping("/books/rent")
    public ResponseEntity<String> addNewBookToClient(@RequestBody BookRentDto bookRentDto) {

        Optional<Client> client = clientRepository.findById(bookRentDto.getUserId());
        Optional<Book> book = bookRepository.findById(bookRentDto.getBookId());

        if (!client.isPresent()) {
            throw new NotFoundException("No such client");
        }

        if (!book.isPresent()) {
            throw new NotFoundException("No such book");
        }

        if (isBookAlreadyRented(client.get(), book.get())) {
            throw new BadRequestException("Book is already rented by the client");

        }

        RentedBook rentedBook = new RentedBook();
        rentedBook.setClient(client.get());
        rentedBook.setBook(book.get());
        rentedBook.setLocalDate(LocalDate.now());

        client.get().getRentedBooks().add(rentedBook);

        return ResponseEntity.ok(objectMapper.writeValueAsString(rentedBook));
    }

    @SneakyThrows
    @GetMapping("/")
    public ResponseEntity<String> getAllClients() {
        return ResponseEntity.ok(
                objectMapper.writeValueAsString(clientRepository.findAll())
        );
    }

    @SneakyThrows
    @GetMapping("/rented-books")
    public ResponseEntity<String> getClientsWithRentedBooks() {
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            throw new NotFoundException("There is no clients");
        }
        List<Client> clientsWithBooks = new ArrayList<>();
        for (Client client : clients) {
            if (!client.getRentedBooks().isEmpty()) {
                clientsWithBooks.add(client);
            }
        }

        return ResponseEntity.ok(
                objectMapper.writeValueAsString(clientsWithBooks)
        );
    }

    @SneakyThrows
    @DeleteMapping("/")
    public ResponseEntity<String> deleteClient(
            @RequestParam(name = "client_id") Integer clientId) {

        Client client = getClientOrThrowException(clientId);
        clientRepository.delete(client);
        return ResponseEntity.ok(String.format("Client with id %s was deleted", clientId));
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

    private boolean isBookAlreadyRented(Client client, Book book) {
        for (RentedBook rentedBook : client.getRentedBooks()) {
            if (rentedBook.getBook().equals(book)) {
                return true;
            }
        }
        return false;
    }

}
