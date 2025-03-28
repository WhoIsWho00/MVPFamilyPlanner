package com.example.familyplanner.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Error response")
public class ErrorResponseDto {
    @Schema(description = "Error message", example = "Invalid token")
    private String error;
}