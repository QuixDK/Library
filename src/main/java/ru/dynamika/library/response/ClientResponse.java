package ru.dynamika.library.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dynamika.library.model.Client;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class ClientResponse {

    Client client;
    String message;
}
