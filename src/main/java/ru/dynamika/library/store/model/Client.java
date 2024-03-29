package ru.dynamika.library.store.model;


import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"client_id"})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("client_id")
    int id;

    @Column(nullable = false)
    String fullName;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy"
    )
    LocalDate birthday;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RentedBook> rentedBooks;

}
