package com.example.familyplanner.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserAlreadyExistExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "User already exists";
        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> {
            throw new UserAlreadyExistException(message);
        });
        assertEquals(message, exception.getMessage());
    }
}
