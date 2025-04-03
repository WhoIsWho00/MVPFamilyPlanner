package com.example.familyplanner.dto.requests.password;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResetPasswordRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidResetPasswordRequest() {
        ResetPasswordRequest request = new ResetPasswordRequest(
                "123456",
                "NewPassword!23",
                "NewPassword!23"
        );

        Set violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidToken() {
        ResetPasswordRequest request = new ResetPasswordRequest(
                "12345",
                "NewPassword!23",
                "NewPassword!23"
        );

        Set violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    // ...добавьте тесты для других полей...
}
