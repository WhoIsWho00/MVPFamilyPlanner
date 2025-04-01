package com.example.familyplanner.service;

import com.example.familyplanner.dto.requests.TaskRequest;
import com.example.familyplanner.dto.responses.TaskResponseDto;
import com.example.familyplanner.dto.responses.TaskResponseInCalendarDto;
import com.example.familyplanner.entity.Task;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.TaskRepository;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.converter.TaskConverter;

import com.example.familyplanner.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final UserRepository userRepository;

    public Page<TaskResponseDto> getTasks(
            UUID familyId,
            Boolean completed,
            UUID assignedTo,
            Integer priority,
            int page,
            int size,
            String sortBy,
            String direction) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "createdAt";
        }
        Sort sort = direction != null && direction.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage = taskRepository.findTasksWithFilters(
                familyId, completed, assignedTo, priority, pageable);

        return taskPage.map(taskConverter::convertToDto);
    }
    public TaskResponseDto getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + id));

        return taskConverter.convertToDto(task);
    }

    public void createTask(TaskRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setAssignedTo(user);
        task.setCreatedBy(user);

        taskRepository.save(task);
    }


    public List<TaskResponseInCalendarDto> getTasksBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Task> taskList = taskRepository.findByDueDateBetween(startDate, endDate);
        return taskConverter.convertTasksToDto(taskList);
    }

    public void deleteTask(UUID id) {
        if (id == null) {
            throw new NotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}