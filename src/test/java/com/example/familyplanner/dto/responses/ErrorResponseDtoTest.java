package com.example.familyplanner.dto.responses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseDtoTest {

    @Test
    void testErrorResponseDto() {
        String error = "Validation error";

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .error(error)
                .build();

        assertEquals(error, errorResponseDto.getError());
    }
}
