package com.example.familyplanner.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    private String error;
}