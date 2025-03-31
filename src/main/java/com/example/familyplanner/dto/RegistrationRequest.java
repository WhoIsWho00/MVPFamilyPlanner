package com.example.familyplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//нужны тесты
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must not contain any special characters")
        //^ — начало строки
        //[a-zA-Z0-9]+ — только латинские буквы и цифры, не менее одного символа
        //$ — конец строки
        @Size(min = 2, message = "Username should have at least 2 symbols")
        @Size(max = 15, message = "Username can't be more than 15 symbols")
        @Schema(description = "User's username", example = "JohnDoe", required = true)
        private String username;

        @Email(message = "invalid Email format")
        @NotBlank
        @Size(max = 40)
        @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
        private String email;
        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$",
                message = "Password must contain only Latin letters, digits, and special characters")
        @Pattern(
                regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
                message = "Password must contain at least one special character (!@#$%^&* etc.)")
        @Size(min = 8, message = "Password should have at least 8 symbols")
        @Size(max = 25, message = "Password cant be bigger than 25 symbols")
        @Schema(
                description = "User's password (min 8 chars, must include special character, only Latin letters allowed)",
                example = "Password!23",
                required = true)
        private String password;



        @Schema(description = "User's chosen avatar identifier", example = "avatar1")
        @Column(name = "avatar_id")
        private String avatarId;

        @Schema(description = "User's age", example = "25", minimum = "5", maximum = "100")
        private Integer age;
    }

