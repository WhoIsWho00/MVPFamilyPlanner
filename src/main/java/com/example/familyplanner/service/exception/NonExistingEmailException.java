package com.example.familyplanner.service.exception;

public class NonExistingEmailException extends RuntimeException {
    public NonExistingEmailException(String message) {
        super(message);
    }
}
