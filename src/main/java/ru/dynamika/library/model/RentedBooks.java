package ru.dynamika.library.model;

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
public class RentedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    LocalDateTime rentalTimestamp;

    @Override
    public String toString() {
        return "RentedBooks{" +
                "id=" + id +
                ", client=" + client +
                ", book=" + book +
                ", rentalTimestamp=" + rentalTimestamp +
                '}';
    }
}
