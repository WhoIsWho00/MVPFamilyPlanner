package com.example.familyplanner.dto.responses.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskUpdateResponse {

    @Schema(description = "Title of the task", example = "Buy groceries")
    @Size(min = 10, max = 20)
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Buy milk, bread, and eggs from the store")
    private String description;

    @Schema(description = "Due date for the task in format YYYY-MM-DD", example = "2025-03-31")
    @NotBlank
    private LocalDate dueDate;
}
