package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccessDeniedExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Access is denied";
        AccessDeniedException exception = new AccessDeniedException(message);
        assertEquals(message, exception.getMessage());
    }
}
