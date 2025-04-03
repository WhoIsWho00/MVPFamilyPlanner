package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlreadyExistExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Entity already exists";
        AlreadyExistException exception = new AlreadyExistException(message);
        assertEquals(message, exception.getMessage());
    }
}
