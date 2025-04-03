package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpiredTokenExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Token has expired";
        ExpiredTokenException exception = new ExpiredTokenException(message);
        assertEquals(message, exception.getMessage());
    }
}
