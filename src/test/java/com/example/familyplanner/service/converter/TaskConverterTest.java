package com.example.familyplanner.service.converter;

import com.example.familyplanner.dto.responses.task.TaskResponseDto;
import com.example.familyplanner.dto.responses.task.TaskResponseInCalendarDto;
import com.example.familyplanner.entity.Task;
import com.example.familyplanner.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskConverterTest {

    private TaskConverter taskConverter;
    private UserConverter userConverter;

    @BeforeEach
    void setUp() {
        userConverter = mock(UserConverter.class);
        taskConverter = new TaskConverter(userConverter);
    }

    @Test
    void convertToDto_ShouldReturnTaskResponseDto() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Task Description");
        task.setDueDate("2023-10-01");
        task.setCreatedAt("2023-09-01");
        task.setCompleted(false);
        task.setStatus("OPEN");
        task.setFamilyId(100L);
        task.setPriority("HIGH");

        User assignedTo = new User();
        assignedTo.setUsername("assignedUser");
        task.setAssignedTo(assignedTo);

        when(userConverter.createDtoFromUser(assignedTo)).thenReturn(null);

        TaskResponseDto dto = taskConverter.convertToDto(task);

        assertNotNull(dto);
        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getTitle(), dto.getTitle());
        assertEquals(task.getDescription(), dto.getDescription());
        assertEquals(task.getDueDate(), dto.getDueDate());
        assertEquals(task.getCreatedAt(), dto.getCreatedAt());
        assertEquals(task.isCompleted(), dto.isCompleted());
        assertEquals(task.getStatus(), dto.getStatus());
        assertEquals(task.getFamilyId(), dto.getFamilyId());
        assertEquals(task.getPriority(), dto.getPriority());
    }

    @Test
    void convertTasksToDto_ShouldReturnListOfTaskResponseInCalendarDto() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");

        List<Task> taskList = Arrays.asList(task1, task2);

        List<TaskResponseInCalendarDto> dtoList = taskConverter.convertTasksToDto(taskList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(task1.getId(), dtoList.get(0).getId());
        assertEquals(task2.getId(), dtoList.get(1).getId());
    }

    @Test
    void convertTasksToDto_ShouldThrowExceptionForNullTaskList() {
        assertThrows(NullPointerException.class, () -> taskConverter.convertTasksToDto(null));
    }
}
