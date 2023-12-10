package com.effectivemobile.taskmanager.comment.dto;

import com.effectivemobile.taskmanager.task.dto.TaskShortDto;
import com.effectivemobile.taskmanager.user.dto.UserShortDto;
import lombok.Data;

@Data
public class CommentFullDto {

    private final Long id;

    private final UserShortDto author;

    private final TaskShortDto task;

    private final String commentText;
}
