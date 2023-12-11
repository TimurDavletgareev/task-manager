package com.effectivemobile.taskmanager.task.controller;

import com.effectivemobile.taskmanager.comment.dto.CommentFullDto;
import com.effectivemobile.taskmanager.comment.dto.NewCommentDto;
import com.effectivemobile.taskmanager.comment.service.CommentService;
import com.effectivemobile.taskmanager.task.dto.NewTaskDto;
import com.effectivemobile.taskmanager.task.dto.TaskFullDto;
import com.effectivemobile.taskmanager.task.dto.TaskShortDto;
import com.effectivemobile.taskmanager.task.dto.TaskUpdateDto;
import com.effectivemobile.taskmanager.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "Задачи", description = "API для работы с задачами")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    @ApiOperation(value = "Добавление задачи")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskFullDto addTask(@RequestHeader("X-Author-User-Id") Long userId,
                               @RequestBody @Valid NewTaskDto newTaskDto) {

        return taskService.addTask(userId, newTaskDto);
    }

    @ApiOperation(value = "Обновление задачи автором")
    @PatchMapping("/update/{taskId}")
    public TaskFullDto updateTask(@RequestHeader("X-Author-User-Id") Long userId,
                                  @PathVariable @NotNull Long taskId,
                                  @RequestBody @Valid TaskUpdateDto taskUpdateDto) {

        return taskService.updateTask(userId, taskId, taskUpdateDto);
    }

    @ApiOperation(value = "Изменение  статуса задачи исполнителем")
    @PatchMapping("/status/{taskId}")
    public TaskFullDto setTaskStatusByExecutor(@RequestHeader("X-Executor-User-Id") Long userId,
                                               @PathVariable @NotNull Long taskId,
                                               @RequestParam(value = "status") String newStatus) {

        return taskService.setTaskStatusByExecutor(userId, taskId, newStatus);
    }

    @ApiOperation(value = "Получение задачи по id")
    @GetMapping("/{taskId}")
    public TaskFullDto getById(@PathVariable @NotNull Long taskId) {

        return taskService.getById(taskId);
    }

    @ApiOperation(value = "Получение списка задач по id автора")
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

    @ApiOperation(value = "Получение списка задач по id исполнителя")
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

    @ApiOperation(value = "Удаление задачи автором")
    @DeleteMapping("/{taskId}")
    public void removeById(@RequestHeader("X-Author-User-Id") Long userId,
                           @PathVariable @NotNull Long taskId) {

        taskService.removeById(userId, taskId);
    }

    /*
        Комментарии к задачам
     */
    @ApiOperation(value = "Добавление комментария к задаче")
    @PostMapping("/{taskId}/comment")
    public CommentFullDto addComment(@PathVariable Long taskId,
                                     @RequestHeader("X-Comment-User-Id") Long userId,
                                     @RequestBody NewCommentDto newCommentDto) {

        return commentService.addComment(taskId, userId, newCommentDto);
    }

    @ApiOperation(value = "Получение списка комментариев к задаче")
    @GetMapping("/{taskId}/comment")
    public List<CommentFullDto> getCommentsByTaskId(@PathVariable Long taskId,
                                                    @RequestParam(value = "from", defaultValue = "0") int from,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {

        return commentService.getByTaskId(taskId, from, size);
    }

    @ApiOperation(value = "Удаление комментария к задаче")
    @DeleteMapping("/{commentId}/comment")
    public void addComment(@PathVariable Long commentId,
                           @RequestHeader("X-Comment-User-Id") Long userId) {

        commentService.removeById(userId, commentId);
    }


}
