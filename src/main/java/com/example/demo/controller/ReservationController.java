package com.example.demo.controller;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.dto.ReservationResponse;
import com.example.demo.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse bookHotel(@Valid @RequestBody ReservationRequest request,
                                         @RequestHeader("X-User-Name") String username) {
        return reservationService.bookHotel(request, username);
    }

    @DeleteMapping("/{reservationUid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable String reservationUid,
                                  @RequestHeader("X-User-Name") String username) {
        reservationService.cancelReservation(reservationUid, username);
    }
}
