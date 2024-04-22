package com.example.demo.models;

import com.example.demo.models.status.LoyaltyStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "loyalty")
@NoArgsConstructor
@Data
public class Loyalty {
    @Id
    private String username;
    private int totalBookings;
    private LoyaltyStatus status;

}
