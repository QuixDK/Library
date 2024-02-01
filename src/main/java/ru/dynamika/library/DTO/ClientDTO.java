package ru.dynamika.library.DTO;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class ClientDTO {

    String fullName;
    Date birthday;
}
