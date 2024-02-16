package ru.dynamika.library.api.dto;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {

    @NotEmpty
    int id;

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    String fullName;

    @NotEmpty
    @JsonUnwrapped
    LocalDate birthday;
}
