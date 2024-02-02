package ru.dynamika.library.request;

import lombok.Data;

import java.util.Date;

@Data
public class ClientUpdateRequestDto {

    int id;
    String fullName;
    Date birthday;
}
