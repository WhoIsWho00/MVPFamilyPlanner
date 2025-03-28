package com.example.familyplanner.repository;


import com.example.familyplanner.entity.PasswordResetToken;
import com.example.familyplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    List<PasswordResetToken> findByUser(User user);
}
