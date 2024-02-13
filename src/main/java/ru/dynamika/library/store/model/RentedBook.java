package ru.dynamika.library.store.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"rented_book_id", "rented_time", "book"})
public class RentedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("rented_book_id")
    int id;


    @ManyToOne
    @JsonBackReference
    Client client;


    @ManyToOne
    @JsonUnwrapped
    Book book;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    @JsonProperty("rented_time")
    LocalDateTime rentalTimestamp;

    @Override
    public String toString() {
        return "RentedBooks{" +
                "id=" + id +
               // ", client=" + client +
                ", book=" + book +
                ", rentalTimestamp=" + rentalTimestamp +
                '}';
    }
}
