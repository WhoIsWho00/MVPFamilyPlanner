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
@Schema(description = "Response after successful authentication")
public class AuthResponseDto {
    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "User details")
    private UserResponseDto user;

    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Schema(description = "Status message", example = "Login successful")
    private String message;
}