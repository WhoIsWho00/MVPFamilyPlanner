package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Resource not found";
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(message);
        });
        assertEquals(message, exception.getMessage());
    }
}
