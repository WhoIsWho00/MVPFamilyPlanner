package com.example.familyplanner.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request model for task details")
public class TaskRequest {
    @Schema(description = "Title of the task", example = "Buy groceries")
    @Size(min = 10, max = 20)
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Buy milk, bread, and eggs from the store")
    private String description;

    @Schema(description = "Due date for the task in format YYYY-MM-DD", example = "2025-03-31")
    @NotBlank
    //время только в виде даты приходит. Без времени
    private LocalDateTime dueDate;

}
