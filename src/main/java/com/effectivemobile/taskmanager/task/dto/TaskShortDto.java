package com.effectivemobile.taskmanager.task.dto;

import com.effectivemobile.taskmanager.task.model.TaskStatus;
import lombok.Data;

@Data
public class TaskShortDto {

    private final Long id;
    private final String title;
    private final TaskStatus status;
}
