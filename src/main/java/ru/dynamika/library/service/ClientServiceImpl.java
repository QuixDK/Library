package ru.dynamika.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dynamika.library.DTO.ClientDTO;
import ru.dynamika.library.model.Book;
import ru.dynamika.library.model.Client;
import ru.dynamika.library.repository.ClientRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepo clientRepo;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public String getAllClients() {
        return objectMapper.writeValueAsString(clientRepo.findAll());
    }

    @Override
    public void saveNewClient(ClientDTO clientDTO) {
        log.info("Save new book: " + clientRepo.save(Client.builder()
                .fullName(clientDTO.getFullName())
                .birthday(clientDTO.getBirthday())
                .build()
        ));
    }

    @Override
    public String updateClient(Client client) {
        return null;
    }
}
