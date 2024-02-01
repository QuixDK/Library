package ru.dynamika.library.DTO;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {

    String name;
    String author;
    String ISBN;

}
