package ru.dynamika.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.model.RentedBooks;
import ru.dynamika.library.repository.BookRepo;
import ru.dynamika.library.repository.ClientRepo;
import ru.dynamika.library.repository.RentedBooksRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepo clientRepo;
    private final RentedBooksRepo rentedBooksRepo;
    private final BookRepo bookRepo;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public String getAllClients() {
        return objectMapper.writeValueAsString(clientRepo.findAll());
    }

    @Override
    public String saveNewClient(ClientDto clientDTO) {
        log.info("Save new client: " + clientRepo.save(Client.builder()
                .fullName(clientDTO.getFullName())
                .birthday(clientDTO.getBirthday())
                .rentedBooks(new ArrayList<>())
                .build()
        ));
        return "Save new client";
    }

    @Override
    public String updateClient(Client client) {
        if (!clientRepo.existsById(client.getId())) {
            return "No such client";
        }
        log.info("Update client: " + clientRepo.save(client));
        return "Update client: " + clientRepo.findById(client.getId());
    }

    public String addNewBookToClient(BookRentDto bookRentDto) {
        if (!clientRepo.existsById(bookRentDto.getUserId())) {
            return "No such client";
        }
        if (!bookRepo.existsByIsbn(bookRentDto.getIsbn())) {
            return "No such book";
        }
        Client client = clientRepo.findById(bookRentDto.getUserId()).get();
        Book book = bookRepo.findByIsbn(bookRentDto.getIsbn());

        if (isBookAlreadyRented(client, book)) {
            return "Book is already rented by the client";
        }

        RentedBooks rentedBooks = new RentedBooks();
        rentedBooks.setClient(client);
        rentedBooks.setBook(book);
        rentedBooks.setRentalTimestamp(LocalDateTime.now());

        client.getRentedBooks().add(rentedBooks);

        rentedBooksRepo.save(rentedBooks);
        clientRepo.save(client);

        return "Client rented a new book";
    }


    @SneakyThrows
    @Override
    public String getAllReadingClients() {
        List<Client> clients = clientRepo.findAll();
        if (clients.isEmpty()) {
            return "There is no clients";
        }
        return "";
    }

    private boolean isBookAlreadyRented(Client client, Book book) {
        for (RentedBooks rentedBooks : client.getRentedBooks()) {
            if (rentedBooks.getBook().equals(book)) {
                return true;
            }
        }
        return false;
    }
}
