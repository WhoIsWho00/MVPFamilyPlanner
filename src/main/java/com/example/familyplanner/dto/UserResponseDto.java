package com.example.familyplanner.dto;

import com.example.familyplanner.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response model containing user details")
public class UserResponseDto {
    @Schema(description = "Unique identifier for the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "User's username", example = "johndoe")
    private String username;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User's role in the system")
    private Role role;

    @Schema(description = "Identifier for the user's selected avatar", example = "avatar1")
    private String avatarId;

    @Schema(description = "User's age", example = "25")
    private Integer age;

}
