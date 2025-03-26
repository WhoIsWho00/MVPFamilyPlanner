package com.example.familyplanner.controller;

import com.example.familyplanner.dto.TaskResponseDto;
import com.example.familyplanner.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management API")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Get tasks with pagination and filtering",
            description = "Retrieves a paginated list of tasks with optional filtering",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getTasks(
            @Parameter(description = "Filter by family ID") @RequestParam(required = false) UUID familyId,
            @Parameter(description = "Filter by completion status") @RequestParam(required = false) Boolean completed,
            @Parameter(description = "Filter by assigned user ID") @RequestParam(required = false) UUID assignedTo,
            @Parameter(description = "Filter by priority level (1-5)") @RequestParam(required = false) Integer priority,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (ASC or DESC)") @RequestParam(defaultValue = "DESC") String direction) {

        Page<TaskResponseDto> tasks = taskService.getTasks(
                familyId, completed, assignedTo, priority, page, size, sortBy, direction);

        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Get task by ID",
            description = "Retrieves a specific task by its ID",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @Parameter(description = "Task ID", required = true)
            @PathVariable UUID id) {

        TaskResponseDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
}