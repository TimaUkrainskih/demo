package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationRequest {
    @NotNull
    @NotEmpty
    @Size(min = 36, max = 36)
    private String hotelUid;
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
    @NotNull
    @FutureOrPresent
    private LocalDate endDate;

}
