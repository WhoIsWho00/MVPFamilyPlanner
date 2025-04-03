package com.example.familyplanner.dto.requests.task;

import com.example.familyplanner.entity.TaskStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTaskRequest() {
        TaskRequest request = new TaskRequest(
                "Buy groceries",
                "Buy milk, bread, and eggs from the store",
                LocalDate.of(2025, 3, 31),
                TaskStatus.NEW,
                3
        );

        Set violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidTitle() {
        TaskRequest request = new TaskRequest(
                "Short",
                "Valid description",
                LocalDate.of(2025, 3, 31),
                TaskStatus.NEW,
                3
        );

        Set violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    // ...добавьте тесты для других полей...
}
