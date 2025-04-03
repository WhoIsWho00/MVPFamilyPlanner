package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidTokenExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Invalid token";
        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            throw new InvalidTokenException(message);
        });
        assertEquals(message, exception.getMessage());
    }
}
