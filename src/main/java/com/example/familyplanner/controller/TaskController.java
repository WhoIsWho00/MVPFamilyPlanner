package com.example.familyplanner.controller;

import com.example.familyplanner.dto.requests.TaskRequest;
import com.example.familyplanner.dto.responses.TaskResponseDto;
import com.example.familyplanner.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
                    @ApiResponse(responseCode = "401", description = "Unauthorized (authentication required)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                        {
                          "timestamp": "2025-03-25T16:26:19.597Z",
                          "status": 401,
                          "error": "Unauthorized",
                          "message": "Authentication required. Please log in.",
                          "path": "/api/tasks"
                        }
                        """

                            ))),
                    @ApiResponse(responseCode = "403", description = "Forbidden (insufficient permissions)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                        {
                          "timestamp": "2025-03-25T16:26:19.597Z",
                          "status": 403,
                          "error": "Forbidden",
                          "message": "You do not have permission to access these tasks.",
                          "path": "/api/tasks"
                        }
                        """
                            ))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 404,
                                              "error": "Not Found",
                                              "message": "",
                                              "path": "/api/tasks"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 500,
                                              "error": "Internal Server Error",
                                              "message": "An unexpected error occurred.",
                                              "path": "/api/tasks"
                                            }
                                            """
                            )))
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
                    @ApiResponse(responseCode = "201", description = "Task successfully found",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "task": {
                                                "id": 2,
                                                "title": "new_task",
                                              },
                                              "message": "Task successfully created",
                                              "status": "success"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "401", description = "Task validation failed",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 401,
                                              "error": "Bad Request",
                                              "message": {
                                              "title": "title is required"
                                              },
                                              "path": "/api/tasks/2"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 404,
                                              "error": "Not Found",
                                              "message": "",
                                              "path": "/api/tasks/2"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 500,
                                              "error": "Internal Server Error",
                                              "message": "An unexpected error occurred.",
                                              "path": "/api/tasks"
                                            }
                                            """
                            )))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @Parameter(description = "Task ID", required = true)
            @PathVariable UUID id) {

        TaskResponseDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @Operation(
            summary = "Create a new task for the authenticated user",
            description = "Creates a new task and associates it with the currently authenticated user. The task will be saved to the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task successfully created",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "task": {
                                                "id": 2,
                                                "title": "new_task",
                                              },
                                              "message": "Task successfully created",
                                              "status": "success"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "400", description = "Task validation failed",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": {
                                              "title": "title is required"
                                              },
                                              "path": "/api/tasks/"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "403", description = "Forbidden (access denied)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 403,
                                              "error": "Forbidden",
                                              "message": "Access denied. Insufficient permissions.",
                                              "path": "/api/tasks"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 500,
                                              "error": "Internal Server Error",
                                              "message": "An unexpected error occurred.",
                                              "path": "/api/tasks"
                                            }
                                            """
                            )))

            }
    )
    @PostMapping
    public ResponseEntity<String> createTask(
            Principal principal,
            @Valid @RequestBody TaskRequest request) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = principal.getName();
        taskService.createTask(request, email);

        return ResponseEntity.ok("Task created successfully");
    }

}