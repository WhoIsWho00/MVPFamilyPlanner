package com.example.familyplanner.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AvatarIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAvatarId {
    String message() default "Wrong avatar ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
