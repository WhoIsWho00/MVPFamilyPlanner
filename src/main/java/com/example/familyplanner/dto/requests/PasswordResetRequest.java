package com.example.familyplanner.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request to initiate password reset")
public class PasswordResetRequest {
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User's registered email address", example = "user@example.com", required = true)
    private String email;
}