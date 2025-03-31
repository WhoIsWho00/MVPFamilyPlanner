package com.example.familyplanner.service.converter;

import com.example.familyplanner.dto.responses.TaskResponseDto;
import com.example.familyplanner.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final UserConverter userConverter;

    public TaskResponseDto convertToDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setCompleted(task.isCompleted());
        dto.setStatus(task.getStatus());
        dto.setFamilyId(task.getFamilyId());
        dto.setPriority(task.getPriority());

        if (task.getAssignedTo() != null) {
            dto.setAssignedTo(userConverter.createDtoFromUser(task.getAssignedTo()));
        }
        if (task.getCreatedBy() != null) {
            dto.setCreatedBy(userConverter.createDtoFromUser(task.getCreatedBy()));
        }

        return dto;
    }
}