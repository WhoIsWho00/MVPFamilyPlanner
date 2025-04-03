package com.example.familyplanner.dto.responses.task;

import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.entity.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskResponseDtoTest {

    @Test
    void testTaskResponseDto() {
        UUID id = UUID.randomUUID();
        String title = "Buy groceries";
        String description = "We need milk, eggs, and bread.";
        LocalDate dueDate = LocalDate.of(2025, 3, 31);
        LocalDate createdAt = LocalDate.of(2023, 10, 1);
        boolean completed = false;
        TaskStatus status = TaskStatus.IN_PROGRESS;
        UserResponseDto assignedTo = new UserResponseDto();
        UserResponseDto createdBy = new UserResponseDto();
        UUID familyId = UUID.randomUUID();
        Integer priority = 3;

        TaskResponseDto taskResponseDto = new TaskResponseDto(id, title, description, dueDate, createdAt, completed, status, assignedTo, createdBy, familyId, priority);

        assertEquals(id, taskResponseDto.getId());
        assertEquals(title, taskResponseDto.getTitle());
        assertEquals(description, taskResponseDto.getDescription());
        assertEquals(dueDate, taskResponseDto.getDueDate());
        assertEquals(createdAt, taskResponseDto.getCreatedAt());
        assertEquals(completed, taskResponseDto.isCompleted());
        assertEquals(status, taskResponseDto.getStatus());
        assertEquals(assignedTo, taskResponseDto.getAssignedTo());
        assertEquals(createdBy, taskResponseDto.getCreatedBy());
        assertEquals(familyId, taskResponseDto.getFamilyId());
        assertEquals(priority, taskResponseDto.getPriority());
    }
}
