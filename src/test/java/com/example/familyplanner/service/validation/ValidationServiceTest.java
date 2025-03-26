package com.example.familyplanner.service.validation;

import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ValidationService validationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userExists_ShouldReturnTrueWhenUserExists() {

        String email = "existing@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        boolean result = validationService.userExists(email);

        assertTrue(result);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void userExists_ShouldReturnFalseWhenUserDoesNotExist() {

        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = validationService.userExists(email);

        assertFalse(result);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void userExists_ShouldHandleNullEmail() {

        String email = null;
        when(userRepository.findByEmail(null)).thenReturn(Optional.empty());

        boolean result = validationService.userExists(email);

        assertFalse(result);
        verify(userRepository).findByEmail(email);
    }
}