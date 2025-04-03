package com.example.familyplanner.dto.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateProfileRequestTest {

    @Test
    void testUpdateProfileRequest() {
        String username = "JohnDoe";
        String email = "john.doe@example.com";
        String avatarId = "avatar1";
        Integer age = 25;

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(username, email, avatarId, age);

        assertEquals(username, updateProfileRequest.getUsername());
        assertEquals(email, updateProfileRequest.getEmail());
        assertEquals(avatarId, updateProfileRequest.getAvatarId());
        assertEquals(age, updateProfileRequest.getAge());
    }
}
