package com.effectivemobile.taskmanager.task.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewTaskDto {

    @NotNull
    @NotBlank
    @Size(min = 2, message = "size must be between 2 and 50")
    @Size(max = 50, message = "size must be between 2 and 50")
    private final String title;

    @NotNull
    @NotBlank
    @Size(min = 2, message = "size must be between 2 and 500")
    @Size(max = 500, message = "size must be between 2 and 500")
    private final String description;

    @NotNull
    private final Long executorId;

    @NotNull
    private final String priority;
}
