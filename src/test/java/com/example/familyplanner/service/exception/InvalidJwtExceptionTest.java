package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidJwtExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Invalid JWT";
        InvalidJwtException exception = assertThrows(InvalidJwtException.class, () -> {
            throw new InvalidJwtException(message);
        });
        assertEquals(message, exception.getMessage());
    }
}
