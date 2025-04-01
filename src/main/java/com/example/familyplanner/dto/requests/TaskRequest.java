package com.example.familyplanner.dto.requests;

import com.example.familyplanner.entity.TaskStatus;
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
    //время только в виде даты приходит. Без времени
    private LocalDateTime dueDate;

    @Schema(description = "Task status", example = "NEW")
    private TaskStatus status;

    @Schema(description = "Task priority (1-5, where 5 is highest)", example = "3")
    private Integer priority;

}
