package ru.dynamika.library.store.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.api.dto.BookRentDto;
import ru.dynamika.library.api.dto.ClientDto;
import ru.dynamika.library.store.model.Book;
import ru.dynamika.library.store.model.Client;
import ru.dynamika.library.store.model.RentedBook;
import ru.dynamika.library.store.repository.BookRepository;
import ru.dynamika.library.store.repository.ClientRepository;
import ru.dynamika.library.store.repository.RentedBookRepository;
import ru.dynamika.library.api.request.ClientUpdateRequestDto;
import ru.dynamika.library.api.response.ClientResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RentedBookRepository rentedBookRepository;
    private final BookRepository bookRepository;

    @Override
    @SneakyThrows
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public ClientResponse createClient(ClientDto clientDTO) {
        Client client = Client.builder()
                .fullName(clientDTO.getFullName())
                .birthday(clientDTO.getBirthday())
                .rentedBooks(new ArrayList<>())
                .build();

        log.info("Save new client: " + clientRepository.save(client));
        return ClientResponse.builder()
                .client(client)
                .message("Save new client")
                .build();
    }

    @Override
    public ClientResponse updateClient(ClientUpdateRequestDto clientUpdateRequestDto) {
        if (!clientRepository.existsById(clientUpdateRequestDto.getId())) {
            return ClientResponse.builder()
                    .client(null)
                    .message("No such client")
                    .build();
        }
        Client updatedClient = clientRepository.findById(clientUpdateRequestDto.getId()).get();
        updatedClient.setBirthday(clientUpdateRequestDto.getBirthday());
        updatedClient.setFullName(clientUpdateRequestDto.getFullName());
        log.info("Update client: " + clientRepository.save(updatedClient));

        return ClientResponse.builder()
                .client(updatedClient)
                .message("Client was updated")
                .build();
    }

    public ClientResponse addNewBookToClient(BookRentDto bookRentDto) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setClient(null);

        if (!clientRepository.existsById(bookRentDto.getUserId())) {
            clientResponse.setMessage("No such client");
            return clientResponse;
        }
        if (!bookRepository.existsById(bookRentDto.getBookId())) {
            clientResponse.setMessage("No such book");
            return clientResponse;
        }
        Client client = clientRepository.findById(bookRentDto.getUserId()).get();
        Book book = bookRepository.findById(bookRentDto.getBookId()).get();

        if (isBookAlreadyRented(client, book)) {
            clientResponse.setMessage("Book is already rented by the client");
            return clientResponse;
        }

        RentedBook rentedBook = new RentedBook();
        rentedBook.setClient(client);
        rentedBook.setBook(book);
        rentedBook.setLocalDate(LocalDate.now());

        client.getRentedBooks().add(rentedBook);

        log.info("New rented book: " + rentedBookRepository.save(rentedBook));
        log.info("Client update rented books: " + clientRepository.save(client));

        clientResponse.setClient(client);
        clientResponse.setMessage("Client rented a new book");
        return clientResponse;
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
