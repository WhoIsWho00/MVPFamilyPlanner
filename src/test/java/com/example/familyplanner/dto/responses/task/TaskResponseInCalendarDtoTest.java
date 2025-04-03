package com.example.familyplanner.dto.responses.task;

import com.example.familyplanner.dto.responses.UserResponseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskResponseInCalendarDtoTest {

    @Test
    void testTaskResponseInCalendarDto() {
        UUID id = UUID.randomUUID();
        String title = "Buy groceries";
        String description = "We need milk, eggs, and bread.";
        LocalDate dueDate = LocalDate.of(2023, 10, 15);
        LocalDate createdAt = LocalDate.of(2023, 10, 1);
        boolean completed = false;
        UserResponseDto assignedTo = new UserResponseDto(UUID.randomUUID(), "John Doe", "john.doe@example.com");
        UserResponseDto createdBy = new UserResponseDto(UUID.randomUUID(), "Jane Smith", "jane.smith@example.com");
        Integer priority = 3;

        TaskResponseInCalendarDto task = new TaskResponseInCalendarDto(
                id, title, description, dueDate, createdAt, completed, assignedTo, createdBy, priority
        );

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(createdAt, task.getCreatedAt());
        assertEquals(completed, task.isCompleted());
        assertEquals(assignedTo, task.getAssignedTo());
        assertEquals(createdBy, task.getCreatedBy());
        assertEquals(priority, task.getPriority());
    }

    @Test
    void testNoArgsConstructor() {
        TaskResponseInCalendarDto task = new TaskResponseInCalendarDto();
        assertNotNull(task);
    }

    @Test
    void testSettersAndGetters() {
        TaskResponseInCalendarDto task = new TaskResponseInCalendarDto();

        UUID id = UUID.randomUUID();
        task.setId(id);
        assertEquals(id, task.getId());

        String title = "New Task";
        task.setTitle(title);
        assertEquals(title, task.getTitle());

        String description = "Task description";
        task.setDescription(description);
        assertEquals(description, task.getDescription());

        LocalDate dueDate = LocalDate.of(2023, 11, 1);
        task.setDueDate(dueDate);
        assertEquals(dueDate, task.getDueDate());

        LocalDate createdAt = LocalDate.of(2023, 10, 1);
        task.setCreatedAt(createdAt);
        assertEquals(createdAt, task.getCreatedAt());

        boolean completed = true;
        task.setCompleted(completed);
        assertTrue(task.isCompleted());

        UserResponseDto assignedTo = new UserResponseDto(UUID.randomUUID(), "Alice", "alice@example.com");
        task.setAssignedTo(assignedTo);
        assertEquals(assignedTo, task.getAssignedTo());

        UserResponseDto createdBy = new UserResponseDto(UUID.randomUUID(), "Bob", "bob@example.com");
        task.setCreatedBy(createdBy);
        assertEquals(createdBy, task.getCreatedBy());

        Integer priority = 5;
        task.setPriority(priority);
        assertEquals(priority, task.getPriority());
    }
}
