package com.example.familyplanner.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PasswordResetToken {
    @Id @GeneratedValue
    private Long id;
    private String token;
    private LocalDateTime expiryDate;

    @OneToOne
    private User user;
}