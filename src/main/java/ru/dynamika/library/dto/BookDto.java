package ru.dynamika.library.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto {

    String name;
    String author;
    String isbn;

}
