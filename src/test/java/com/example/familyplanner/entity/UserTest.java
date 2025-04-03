package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserEntity() {
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "password123";
        Role role = new Role(UUID.randomUUID(), "USER");
        UUID familyID = UUID.randomUUID();
        UUID inviteCode = UUID.randomUUID();
        String avatarId = "avatar123";
        int age = 25;

        User user = new User(id, username, email, password, role, familyID, inviteCode, avatarId, age);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(familyID, user.getFamilyID());
        assertEquals(inviteCode, user.getInviteCode());
        assertEquals(avatarId, user.getAvatarId());
        assertEquals(age, user.getAge());
    }
}
