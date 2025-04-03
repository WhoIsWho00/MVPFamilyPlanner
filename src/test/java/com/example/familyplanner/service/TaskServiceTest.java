package com.example.familyplanner.service;

import com.example.familyplanner.dto.requests.task.TaskRequest;
import com.example.familyplanner.dto.responses.task.TaskResponseDto;
import com.example.familyplanner.entity.Task;
import com.example.familyplanner.entity.TaskStatus;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.TaskRepository;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.converter.TaskConverter;
import com.example.familyplanner.service.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskConverter taskConverter;

    @InjectMocks
    private TaskService taskService;

    private UUID taskId;
    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskId = UUID.randomUUID();
        user = new User();
        user.setEmail("test@example.com");
        task = new Task();
        task.setId(taskId);
        task.setTitle("Test Task");
        task.setCreatedBy(user);
    }

    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskConverter.convertToDto(task)).thenReturn(new TaskResponseDto());

        TaskResponseDto response = taskService.getTaskById(taskId);

        assertNotNull(response);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void testCreateTask_Success() {
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");
        request.setDescription("Description");
        request.setDueDate(LocalDate.now());
        request.setPriority(1);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        taskService.createTask(request, user.getEmail());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTaskStatus_Success() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskConverter.convertToDto(task)).thenReturn(new TaskResponseDto());

        TaskResponseDto response = taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED);

        assertNotNull(response);
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testGetTasksBetweenDates_Success() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();
        when(taskRepository.findByDueDateBetween(startDate, endDate)).thenReturn(List.of(task));
        when(taskConverter.convertTasksToDto(anyList())).thenReturn(List.of());

        List<?> response = taskService.getTasksBetweenDates(startDate, endDate);

        assertNotNull(response);
        verify(taskRepository, times(1)).findByDueDateBetween(startDate, endDate);
    }
}
