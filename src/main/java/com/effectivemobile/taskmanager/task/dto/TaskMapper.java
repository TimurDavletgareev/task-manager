package com.effectivemobile.taskmanager.task.dto;

import com.effectivemobile.taskmanager.task.model.Task;
import com.effectivemobile.taskmanager.task.model.TaskPriority;
import com.effectivemobile.taskmanager.task.model.TaskStatus;
import com.effectivemobile.taskmanager.user.dto.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    public static TaskFullDto modelToFullDto(Task task) {

        return new TaskFullDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                UserMapper.modelToShortDto(task.getAuthor()),
                UserMapper.modelToShortDto(task.getExecutor()),
                TaskStatus.valueOf(task.getStatus()),
                TaskPriority.valueOf(task.getPriority())
        );
    }

    public static TaskShortDto modelToShortDto(Task task) {

        return new TaskShortDto(
                task.getId(),
                task.getTitle(),
                TaskStatus.valueOf(task.getStatus())
        );
    }

    public static List<TaskShortDto> modelToShortDto(Iterable<Task> tasks) {

        List<TaskShortDto> listToReturn = new ArrayList<>();

        for (Task task : tasks) {

            listToReturn.add(modelToShortDto(task));
        }

        return listToReturn;
    }
}
