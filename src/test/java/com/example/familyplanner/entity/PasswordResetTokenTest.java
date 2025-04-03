package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenTest {

    @Test
    void testPasswordResetTokenEntity() {
        Long id = 1L;
        String token = "test-token";
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);

        PasswordResetToken passwordResetToken = new PasswordResetToken(id, token, expiryDate, null);

        assertEquals(id, passwordResetToken.getId());
        assertEquals(token, passwordResetToken.getToken());
        assertEquals(expiryDate, passwordResetToken.getExpiryDate());
        assertNull(passwordResetToken.getUser());
    }

    @Test
    void testIsExpired() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpiryDate(LocalDateTime.now().minusMinutes(1));

        assertTrue(token.isExpired());

        token.setExpiryDate(LocalDateTime.now().plusMinutes(1));

        assertFalse(token.isExpired());
    }
}
