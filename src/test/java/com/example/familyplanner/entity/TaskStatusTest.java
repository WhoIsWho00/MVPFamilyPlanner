package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    @Test
    void testTaskStatusValues() {
        assertEquals("NEW", TaskStatus.NEW.name());
        assertEquals("IN_PROGRESS", TaskStatus.IN_PROGRESS.name());
        assertEquals("COMPLETED", TaskStatus.COMPLETED.name());
        assertEquals("CANCELLED", TaskStatus.CANCELLED.name());
    }
}
