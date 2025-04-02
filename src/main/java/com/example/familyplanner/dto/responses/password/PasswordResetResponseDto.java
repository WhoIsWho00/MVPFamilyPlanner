package com.example.familyplanner.dto.responses.password;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response after password reset completion")
public class PasswordResetResponseDto {
    @Schema(description = "Status message", example = "Your password has been reset successfully")
    private String message;
}