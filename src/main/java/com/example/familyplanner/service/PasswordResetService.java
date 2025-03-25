package com.example.familyplanner.service;

import com.example.familyplanner.entity.PasswordResetToken;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.PasswordResetTokenRepository;
import com.example.familyplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public String createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with that email not found"));


        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30)) // 30 minutes expiry
                .used(false)
                .build();

        tokenRepository.save(resetToken);

        return token;
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid password reset token"));

        if (resetToken.isUsed()) {
            throw new IllegalStateException("This token has already been used");
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("This token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }
}
