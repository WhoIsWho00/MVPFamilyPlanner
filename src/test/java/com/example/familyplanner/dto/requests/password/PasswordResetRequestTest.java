package com.example.familyplanner.dto.requests.password;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordResetRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPasswordResetRequest() {
        PasswordResetRequest request = new PasswordResetRequest(
                "user@example.com"
        );

        Set violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidEmail() {
        PasswordResetRequest request = new PasswordResetRequest(
                "invalid-email"
        );

        Set violations = validator.validate(request);
        assertEquals(1, violations.size());
    }
}
