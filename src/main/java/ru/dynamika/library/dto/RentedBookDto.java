package ru.dynamika.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentedBookDto {
    private BookDto bookDto;
    private LocalDateTime rentalTimestamp;

}
