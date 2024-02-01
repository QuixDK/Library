package ru.dynamika.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRentedBooksDto {

    private int id;
    private String fullName;
    private Date birthday;
    private List<RentedBookDto> rentedBooks;

}
