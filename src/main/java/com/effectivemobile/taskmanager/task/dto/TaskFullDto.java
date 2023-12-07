package com.effectivemobile.taskmanager.task.dto;

import com.effectivemobile.taskmanager.task.model.TaskPriority;
import com.effectivemobile.taskmanager.task.model.TaskStatus;
import com.effectivemobile.taskmanager.user.dto.UserShortDto;
import lombok.Data;

@Data
public class TaskFullDto {

    private final Long id;

    private final String title;

    private final String description;

    private final UserShortDto author;

    private final UserShortDto executor;

    private final TaskStatus status;

    private final TaskPriority priority;
}
