package com.example.demo.models;

import com.example.demo.models.status.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@Data
public class Reservation {
    @Id
    private String reservationUid;
    private String username;
    private ReservationStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "hotelUid", referencedColumnName = "hotelUid")
    private Hotel hotel;
}
