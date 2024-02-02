package ru.dynamika.library.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequestDto {

    int id;
    String name;
    String author;
    String isbn;
}
