package ru.dynamika.library.api.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.jni.Local;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class ClientUpdateRequestDto {

    int id;
    String fullName;
    LocalDate birthday;
}
