package com.example.demo.exceptions.handlers;

import com.example.demo.exceptions.LoyaltyNotFoundException;
import com.example.demo.dto.ResponsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LoyaltyNotFoundExceptionHandler {
    @ExceptionHandler(LoyaltyNotFoundException.class)
    public ResponseEntity<ResponsException> handleException(LoyaltyNotFoundException e) {
        ResponsException respons = new ResponsException(e.getMessage());
        return new ResponseEntity<>(respons, HttpStatus.NOT_FOUND);
    }
}
