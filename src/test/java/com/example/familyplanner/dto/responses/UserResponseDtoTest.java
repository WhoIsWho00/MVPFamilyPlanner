package com.example.familyplanner.dto.responses;

import com.example.familyplanner.entity.Role;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseDtoTest {

    @Test
    void testUserResponseDto() {
        UUID id = UUID.randomUUID();
        String username = "johndoe";
        String email = "john.doe@example.com";
        Role role = Role.USER;
        String avatarId = "avatar1";
        Integer age = 25;

        UserResponseDto userResponseDto = new UserResponseDto(id, username, email, role, avatarId, age);

        assertEquals(id, userResponseDto.getId());
        assertEquals(username, userResponseDto.getUsername());
        assertEquals(email, userResponseDto.getEmail());
        assertEquals(role, userResponseDto.getRole());
        assertEquals(avatarId, userResponseDto.getAvatarId());
        assertEquals(age, userResponseDto.getAge());
    }
}
