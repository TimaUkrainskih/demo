package com.example.demo.exceptions;

public class LoyaltyNotFoundException extends RuntimeException{
   public LoyaltyNotFoundException(String message) {
       super(message);
   }
}
