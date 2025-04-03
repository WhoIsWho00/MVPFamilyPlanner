package com.example.familyplanner.dto.requests.signInUp;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidLoginRequest() {
        LoginRequest request = new LoginRequest(
                "user@example.com",
                "Password!23"
        );

        Set violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidEmail() {
        LoginRequest request = new LoginRequest(
                "invalid-email",
                "Password!23"
        );

        Set violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    // ...добавьте тесты для других полей...
}
