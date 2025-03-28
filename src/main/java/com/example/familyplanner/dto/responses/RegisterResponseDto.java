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
@Schema(description = "Response after successful registration")
public class RegisterResponseDto {
    @Schema(description = "User details")
    private UserResponseDto user;

    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Status message", example = "User successfully registered")
    private String message;

    @Schema(description = "Status code", example = "success")
    private String status;
}