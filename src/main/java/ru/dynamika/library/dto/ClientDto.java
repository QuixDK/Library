package ru.dynamika.library.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {

    String fullName;
    Date birthday;
}
