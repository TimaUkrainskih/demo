package com.example.demo.service;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.dto.ReservationResponse;
import com.example.demo.exceptions.HotelNotFoundException;
import com.example.demo.exceptions.LoyaltyNotFoundException;
import com.example.demo.exceptions.ReservationNotFoundException;
import com.example.demo.models.Hotel;
import com.example.demo.models.Loyalty;
import com.example.demo.models.Reservation;
import com.example.demo.models.status.LoyaltyStatus;
import com.example.demo.models.status.ReservationStatus;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.LoyaltyRepository;
import com.example.demo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final LoyaltyRepository loyaltyRepository;
    private final HotelRepository hotelRepository;

    public ReservationResponse getNumberOfNightsAndTotalAmount(ReservationRequest request, String username) {
        Hotel hotel = hotelRepository
                .findById(request.getHotelUid())
                .orElseThrow(() -> new HotelNotFoundException("Отель не найден"));
        Loyalty loyalty = loyaltyRepository
                .findById(username)
                .orElseThrow(() -> new LoyaltyNotFoundException("Пользователь не найден"));
        int numberOfNights = (int) ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        double discount = 1;
        switch (loyalty.getStatus()) {
            case BRONZE -> discount = 0.95;
            case SILVER -> discount = 0.93;
            case GOLD -> discount = 0.9;
        }
        double totalAmount = numberOfNights * hotel.getPricePerNight() * discount;
        return new ReservationResponse(numberOfNights, totalAmount);
    }


    public ReservationResponse bookHotel(ReservationRequest request, String username) {
        Hotel hotel = hotelRepository
                .findById(request.getHotelUid())
                .orElseThrow(() -> new HotelNotFoundException("Отель не найден"));
        Loyalty loyalty = createLoyalty(username);
        Reservation reservation = createReservation(request,username,hotel);
        reservationRepository.save(reservation);
        loyaltyRepository.save(loyalty);
        return getNumberOfNightsAndTotalAmount(request, username);
    }

    private Loyalty createLoyalty(String username) {
        Loyalty loyalty = loyaltyRepository
                .findById(username)
                .orElse(null);
        if (loyalty == null) {
            loyalty = new Loyalty();
            loyalty.setUsername(username);
            loyalty.setTotalBookings(0);
            loyalty.setStatus(LoyaltyStatus.BRONZE);
            loyaltyRepository.save(loyalty);
        }
        loyalty.setTotalBookings(loyalty.getTotalBookings() + 1);
        if (loyalty.getTotalBookings() >= 20) {
            loyalty.setStatus(LoyaltyStatus.GOLD);
        } else if (loyalty.getTotalBookings() >= 10) {
            loyalty.setStatus(LoyaltyStatus.SILVER);
        }
        return loyalty;
    }

    private Reservation createReservation(ReservationRequest request, String username, Hotel hotel) {
        Reservation reservation = new Reservation();
        reservation.setReservationUid(String.valueOf(UUID.randomUUID()));
        reservation.setUsername(username);
        reservation.setStatus(ReservationStatus.SUCCESS);
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setHotel(hotel);
        return reservation;
    }

    public void cancelReservation(String reservationUid, String username) {
        Reservation reservation = reservationRepository
                .findById(reservationUid)
                .orElseThrow(() -> new ReservationNotFoundException("Бронирование не найдено"));
        if(reservation.getStatus().equals(ReservationStatus.SUCCESS)) {
            reservation.setStatus(ReservationStatus.CANCELED);
            reservationRepository.save(reservation);
            Loyalty loyalty = loyaltyRepository
                    .findById(username)
                    .orElseThrow(() -> new LoyaltyNotFoundException("Пользователь не найден"));
            loyalty.setTotalBookings(loyalty.getTotalBookings() - 1);
            loyaltyRepository.save(loyalty);
        }
    }
}