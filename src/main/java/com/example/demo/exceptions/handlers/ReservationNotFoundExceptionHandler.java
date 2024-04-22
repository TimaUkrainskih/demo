package com.example.demo.exceptions.handlers;

import com.example.demo.exceptions.ReservationNotFoundException;
import com.example.demo.dto.ResponsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationNotFoundExceptionHandler {
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ResponsException> handleException(ReservationNotFoundException e) {
        ResponsException respons = new ResponsException(e.getMessage());
        return new ResponseEntity<>(respons, HttpStatus.NOT_FOUND);
    }
}
