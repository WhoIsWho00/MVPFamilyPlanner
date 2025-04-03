package com.example.familyplanner.dto.responses.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskUpdateResponseTest {

    @Test
    void testTaskUpdateResponse() {
        String title = "Buy groceries";
        String description = "Buy milk, bread, and eggs from the store";
        LocalDate dueDate = LocalDate.of(2025, 3, 31);

        TaskUpdateResponse taskUpdateResponse = new TaskUpdateResponse();
        taskUpdateResponse.setTitle(title);
        taskUpdateResponse.setDescription(description);
        taskUpdateResponse.setDueDate(dueDate);

        assertEquals(title, taskUpdateResponse.getTitle());
        assertEquals(description, taskUpdateResponse.getDescription());
        assertEquals(dueDate, taskUpdateResponse.getDueDate());
    }
}
