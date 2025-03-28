package com.example.familyplanner.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_registration_log")
public class UserRegistrationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private LocalDateTime registrationTime;

    public UserRegistrationLog(String ipAddress, LocalDateTime registrationTime) {
        this.ipAddress = ipAddress;
        this.registrationTime = registrationTime;
    }

    public UserRegistrationLog() {

    }
}
