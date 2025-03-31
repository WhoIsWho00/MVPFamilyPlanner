package com.example.familyplanner.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class AvatarIdValidator implements ConstraintValidator<ValidAvatarId, String> {

    private static final Set<String> ALLOWED_AVATARS = Set.of(
            "avatar1", "avatar2", "avatar3", "avatar4", "avatar5", "avatar6"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || ALLOWED_AVATARS.contains(value);
    }
}
