package ru.dynamika.library.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.dto.BookRentDto;
import ru.dynamika.library.dto.ClientDto;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.model.RentedBook;
import ru.dynamika.library.repository.BookRepository;
import ru.dynamika.library.repository.ClientRepository;
import ru.dynamika.library.repository.RentedBookRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepository clientRepository;
    private final RentedBookRepository rentedBookRepository;
    private final BookRepository bookRepository;

    @Override
    @SneakyThrows
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public String createClient(ClientDto clientDTO) {
        log.info("Save new client: " + clientRepository.save(Client.builder()
                .fullName(clientDTO.getFullName())
                .birthday(clientDTO.getBirthday())
                .rentedBooks(new ArrayList<>())
                .build()
        ));
        return "Save new client";
    }

    @Override
    public String updateClient(Client client) {
        if (!clientRepository.existsById(client.getId())) {
            return "No such client";
        }
        log.info("Update client: " + clientRepository.save(client));
        return "Update client: " + clientRepository.findById(client.getId());
    }

    public String addNewBookToClient(BookRentDto bookRentDto) {
        if (!clientRepository.existsById(bookRentDto.getUserId())) {
            return "No such client";
        }
        if (!bookRepository.existsByIsbn(bookRentDto.getIsbn())) {
            return "No such book";
        }
        Client client = clientRepository.findById(bookRentDto.getUserId()).get();
        Book book = bookRepository.findByIsbn(bookRentDto.getIsbn());

        if (isBookAlreadyRented(client, book)) {
            return "Book is already rented by the client";
        }

        RentedBook rentedBook = new RentedBook();
        rentedBook.setClient(client);
        rentedBook.setBook(book);
        rentedBook.setRentalTimestamp(LocalDateTime.now());

        client.getRentedBooks().add(rentedBook);

        rentedBookRepository.save(rentedBook);
        clientRepository.save(client);

        return "Client rented a new book";
    }

    @SneakyThrows
    @Override
    public List<Client> getClientsWithRentedBooks() {
        return clientRepository.findAll();
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
