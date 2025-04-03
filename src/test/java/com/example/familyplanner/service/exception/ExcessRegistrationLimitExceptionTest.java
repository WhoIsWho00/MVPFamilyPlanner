package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcessRegistrationLimitExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Registration limit exceeded";
        ExcessRegistrationLimitException exception = new ExcessRegistrationLimitException(message);
        assertEquals(message, exception.getMessage());
    }
}
