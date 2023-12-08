package com.effectivemobile.taskmanager.task.service;

import com.effectivemobile.taskmanager.task.dto.NewTaskDto;
import com.effectivemobile.taskmanager.task.dto.TaskFullDto;
import com.effectivemobile.taskmanager.task.dto.TaskShortDto;
import com.effectivemobile.taskmanager.task.dto.TaskUpdateDto;

import java.util.List;

public interface TaskService {

    TaskFullDto addTask(Long authorId, NewTaskDto newTaskDto);

    TaskFullDto updateTask(Long userId, Long taskId, TaskUpdateDto updateDto);

    TaskFullDto setTaskStatusByExecutor(Long userId, Long taskId, String newStatus);

    TaskFullDto getById(Long taskId);

    List<TaskShortDto> getByAuthorId(Long authorId,
                                     String textToSearch,
                                     String status,
                                     String priority,
                                     int from, int size,
                                     String sortBy);

    List<TaskShortDto> getByExecutorId(Long executorId,
                                       String textToSearch,
                                       String status,
                                       String priority,
                                       int from, int size,
                                       String sortBy);

    void removeById(Long userId, Long taskId);


}
