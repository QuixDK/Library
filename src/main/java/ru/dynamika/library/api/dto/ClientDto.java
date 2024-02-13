package ru.dynamika.library.api.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    String fullName;

    @NotEmpty
    Date birthday;
}
