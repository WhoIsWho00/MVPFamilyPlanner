package com.example.familyplanner.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid")
    private String email;
}