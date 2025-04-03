package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationLogTest {

    @Test
    void testUserRegistrationLogEntity() {
        Long id = 1L;
        String ipAddress = "192.168.1.1";
        LocalDateTime registrationTime = LocalDateTime.now();

        UserRegistrationLog log = new UserRegistrationLog(ipAddress, registrationTime);

        assertNull(log.getId());
        assertEquals(ipAddress, log.getIpAddress());
        assertEquals(registrationTime, log.getRegistrationTime());
    }
}
