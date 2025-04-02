package com.example.familyplanner.dto.requests.signInUp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//нужны тесты
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
        @NotBlank
        @Pattern(regexp = "^[\\p{L}0-9\\-]+$", message = "Username must not contain any special characters")
        //^ — начало строки
        //[a-zA-Z0-9]+ — только латинские буквы и цифры, не менее одного символа
        //$ — конец строки
        @Size(min = 2, message = "Username should have at least 2 symbols")
        @Size(max = 15, message = "Username can't be more than 15 symbols")
        @Schema(description = "User's username", example = "JohnDoe", required = true)
        private String username;

        @NotBlank(message = "Email cannot be empty")
//        @Email(message = "Invalid email format")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "Email must be a valid email address with a proper domain")
        @Size(max = 40, message = "Email cannot be longer than 40 characters")
        @Schema(description = "User's email address", example = "blackjack@ua", required = true)
        private String email;

        @NotBlank(message = "Password cannot be empty")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
        @Size(min = 8, message = "Password should have at least 8 symbols")
        @Size(max = 25, message = "Password can't be longer than 25 symbols")
        @Schema(description = "User's password (min 8 chars, must include lowercase, uppercase, digit, and special character)", example = "Password1!", required = true)
        private String password;

        @NotBlank(message = "Avatar must not be empty")
        @Schema(description = "User's chosen avatar identifier", example = "avatar1", required = true)
        @Pattern(regexp = "^avatar[1-6]$", message = "Invalid avatar ID. Must be between avatar1 and avatar6")
        @Column(name = "avatar_id")
        @NotBlank
        private String avatarId;

        @NotNull(message = "Age must not be empty")
        @Min(value = 5, message = "Age must be at least 5")
        @Max(value = 100, message = "Age must not exceed 100")
        @Schema(description = "User's age", example = "25", minimum = "5", maximum = "100")
        @NotNull
        private Integer age;
    }

