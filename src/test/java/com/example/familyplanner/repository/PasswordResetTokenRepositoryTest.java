package com.example.familyplanner.repository;

import com.example.familyplanner.entity.PasswordResetToken;
import com.example.familyplanner.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Test
    void testFindByToken() {
        // Arrange
        PasswordResetToken token = new PasswordResetToken();
        token.setToken("test-token");
        passwordResetTokenRepository.save(token);

        // Act
        Optional<PasswordResetToken> foundToken = passwordResetTokenRepository.findByToken("test-token");

        // Assert
        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getToken()).isEqualTo("test-token");
    }

    @Test
    void testFindByUser() {
        // Arrange
        User user = new User();
        user.setId(1L); // Assuming ID is set manually or via a sequence
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        passwordResetTokenRepository.save(token);

        // Act
        List<PasswordResetToken> tokens = passwordResetTokenRepository.findByUser(user);

        // Assert
        assertThat(tokens).isNotEmpty();
        assertThat(tokens.get(0).getUser()).isEqualTo(user);
    }
}
