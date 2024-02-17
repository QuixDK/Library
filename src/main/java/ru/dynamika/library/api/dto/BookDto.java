package ru.dynamika.library.api.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @NotEmpty
    int id;
    @NotEmpty
    @Size(min = 2, message = "book name should have at least 2 characters")
    String name;
    @NotEmpty
    String author;
    @NotEmpty
    String isbn;

}
