package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskEntity() {
        UUID id = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        LocalDate dueDate = LocalDate.now().plusDays(5);
        LocalDate createdAt = LocalDate.now();
        boolean completed = false;
        TaskStatus status = TaskStatus.NEW;
        UUID familyId = UUID.randomUUID();
        int priority = 1;

        Task task = new Task(id, title, description, dueDate, createdAt, completed, status, null, null, familyId, priority);

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(createdAt, task.getCreatedAt());
        assertFalse(task.isCompleted());
        assertEquals(status, task.getStatus());
        assertEquals(familyId, task.getFamilyId());
        assertEquals(priority, task.getPriority());
    }

    @Test
    void testSetCompleted() {
        Task task = new Task();
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setCompleted(true);

        assertTrue(task.isCompleted());
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void testSetStatus() {
        Task task = new Task();
        task.setStatus(TaskStatus.IN_PROGRESS);

        assertFalse(task.isCompleted());
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }
}
