package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "hotel")
@NoArgsConstructor
@Data
public class Hotel {
    @Id
    private String hotelUid;
    private String nameHotel;
    private String address;
    private Integer pricePerNight;
}

