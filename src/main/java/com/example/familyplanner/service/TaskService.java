package com.example.familyplanner.service;

import com.example.familyplanner.dto.TaskResponseDto;
import com.example.familyplanner.entity.Task;
import com.example.familyplanner.repository.TaskRepository;
import com.example.familyplanner.service.converter.TaskConverter;

import com.example.familyplanner.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;

    public Page<TaskResponseDto> getTasks(
            UUID familyId,
            Boolean completed,
            UUID assignedTo,
            Integer priority,
            int page,
            int size,
            String sortBy,
            String direction) {

        // Default sort by creation date descending if not specified
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
}