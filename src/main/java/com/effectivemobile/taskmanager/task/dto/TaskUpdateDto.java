package com.effectivemobile.taskmanager.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUpdateDto {

    @NotBlank
    @Size(min = 2, message = "size must be between 2 and 50")
    @Size(max = 50, message = "size must be between 2 and 50")
    private final String title;

    @NotBlank
    @Size(min = 2, message = "size must be between 2 and 500")
    @Size(max = 500, message = "size must be between 2 and 500")
    private final String description;

    private final Long executorId;

    private final String priority;
}
