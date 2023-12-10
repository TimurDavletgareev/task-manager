package com.effectivemobile.taskmanager.task.controller;

import com.effectivemobile.taskmanager.task.dto.NewTaskDto;
import com.effectivemobile.taskmanager.task.dto.TaskFullDto;
import com.effectivemobile.taskmanager.task.dto.TaskShortDto;
import com.effectivemobile.taskmanager.task.dto.TaskUpdateDto;
import com.effectivemobile.taskmanager.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskFullDto addTask(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @RequestBody @Valid NewTaskDto newTaskDto) {

        return taskService.addTask(userId, newTaskDto);
    }

    @PatchMapping("/update/{taskId}")
    public TaskFullDto updateTask(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @PathVariable @NotNull Long taskId,
                                  @RequestBody @Valid TaskUpdateDto taskUpdateDto) {

        return taskService.updateTask(userId, taskId, taskUpdateDto);
    }

    @PatchMapping("/status/{taskId}")
    public TaskFullDto setTaskStatusByExecutor(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @PathVariable @NotNull Long taskId,
                                               @RequestParam(value = "status") String newStatus) {

        return taskService.setTaskStatusByExecutor(userId, taskId, newStatus);
    }

    @GetMapping("/{taskId}")
    public TaskFullDto getById(@PathVariable @NotNull Long taskId) {

        return taskService.getById(taskId);
    }

    @GetMapping("/author/{authorId}")
    public List<TaskShortDto> getByAuthorId(@PathVariable @NotNull Long authorId,
                                            @RequestParam(value = "text", required = false) String textToSearch,
                                            @RequestParam(value = "status", required = false) String status,
                                            @RequestParam(value = "priority", required = false) String priority,
                                            @RequestParam(value = "from", defaultValue = "0") int from,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @RequestParam(value = "sort", required = false,
                                                    defaultValue = "id") String sortBy) {

        return taskService.getByAuthorId(authorId, textToSearch, status, priority, from, size, sortBy);
    }

    @GetMapping("/executor/{executorId}")
    public List<TaskShortDto> getByExecutorId(@PathVariable @NotNull Long executorId,
                                              @RequestParam(value = "text", required = false) String textToSearch,
                                              @RequestParam(value = "status", required = false) String status,
                                              @RequestParam(value = "priority", required = false) String priority,
                                              @RequestParam(value = "from", defaultValue = "0") int from,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @RequestParam(value = "sort", defaultValue = "id") String sortBy) {

        return taskService.getByExecutorId(executorId, textToSearch, status, priority, from, size, sortBy);
    }

    @DeleteMapping("/{taskId}")
    public void removeById(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable @NotNull Long taskId) {

        taskService.removeById(userId, taskId);
    }
}
