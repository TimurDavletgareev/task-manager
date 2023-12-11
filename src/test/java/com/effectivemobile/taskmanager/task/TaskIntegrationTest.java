package com.effectivemobile.taskmanager.task;

import com.effectivemobile.taskmanager.error.exception.ConflictOnRequestException;
import com.effectivemobile.taskmanager.error.exception.NotFoundException;
import com.effectivemobile.taskmanager.task.controller.TaskController;
import com.effectivemobile.taskmanager.task.dto.NewTaskDto;
import com.effectivemobile.taskmanager.task.dto.TaskFullDto;
import com.effectivemobile.taskmanager.task.dto.TaskShortDto;
import com.effectivemobile.taskmanager.task.dto.TaskUpdateDto;
import com.effectivemobile.taskmanager.task.model.TaskPriority;
import com.effectivemobile.taskmanager.task.model.TaskStatus;
import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.dto.UserFullDto;
import com.effectivemobile.taskmanager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TaskIntegrationTest {

    private final UserService userService;
    private final TaskController taskController;

    private static Long user1Id;
    private static Long user2Id;

    private static NewTaskDto newTaskDto1;
    private static NewTaskDto newTaskDto2;
    private static NewTaskDto newTaskDto3;
    private static NewTaskDto newTaskDto4;

    @BeforeEach
    void setUp() {

        NewUserDto newUser1 =
                new NewUserDto("user1", "user1@mail.com",
                        "user1Password", "user1Password");
        userService.addUser(newUser1);
        UserFullDto user1 = userService.getByName(newUser1.getName());
        user1Id = user1.getId();

        NewUserDto newUser2 =
                new NewUserDto("user2", "user2@mail.com",
                        "user2Password", "user2Password");
        userService.addUser(newUser2);
        UserFullDto user2 = userService.getByName(newUser2.getName());
        user2Id = user2.getId();

        newTaskDto1 = new NewTaskDto("task1", "descr1", user2Id, "MID");
        newTaskDto2 = new NewTaskDto("task2", "descr2", user2Id, "HIGH");
        newTaskDto3 = new NewTaskDto("task3", "descr3", user1Id, "LOW");
        newTaskDto4 = new NewTaskDto("task4", "descr4", user1Id, "MID");

    }

    @Test
    void shouldAddTask() {

        TaskFullDto taskToCheck = taskController.addTask(user1Id, newTaskDto1);
        assertEquals(newTaskDto1.getTitle(), taskToCheck.getTitle());
        assertEquals(newTaskDto1.getDescription(), taskToCheck.getDescription());
        assertEquals(newTaskDto1.getPriority(), taskToCheck.getPriority().toString());
        assertEquals(user1Id, taskToCheck.getAuthor().getId());
        assertEquals(user2Id, taskToCheck.getExecutor().getId());
        assertEquals(TaskStatus.WAITING, taskToCheck.getStatus());
        assertEquals(TaskPriority.valueOf(newTaskDto1.getPriority()), taskToCheck.getPriority());

        // wrong userId
        assertThrows(NotFoundException.class, () ->
                taskController.addTask(-999L, newTaskDto2));

        // wrong executorId
        NewTaskDto wrongExecutorDto = new NewTaskDto("title", "descr", -999L, "MID");
        assertThrows(NotFoundException.class, () ->
                taskController.addTask(user1Id, wrongExecutorDto));

        // wrong priority
        NewTaskDto wrongPriority = new NewTaskDto("title", "descr", user2Id, "WRONG_PRIORITY");
        assertThrows(ConflictOnRequestException.class, () ->
                taskController.addTask(user1Id, wrongPriority));

        // null title
        NewTaskDto nullTitle = new NewTaskDto(null, "descr", user2Id, "MID");
        assertThrows(Exception.class, () ->
                taskController.addTask(user1Id, nullTitle));
    }

    @Test
    void shouldUpdate() {

        TaskFullDto originalTask = taskController.addTask(user1Id, newTaskDto1);
        Long taskId = originalTask.getId();

        String newTitle = "NewTitle";
        String newDescr = "newDescr";
        Long newExecutorId = user1Id;
        TaskUpdateDto updateDto1 = new TaskUpdateDto(newTitle, newDescr, newExecutorId, null);

        TaskFullDto updatedTask = taskController.updateTask(user1Id, taskId, updateDto1);

        assertEquals(taskId, updatedTask.getId());
        assertEquals(newTitle, updatedTask.getTitle());
        assertEquals(newDescr, updatedTask.getDescription());
        assertEquals(newExecutorId, updatedTask.getExecutor().getId());
        assertEquals(newTaskDto1.getPriority(), updatedTask.getPriority().toString());

        String newPriority = TaskPriority.HIGH.toString();
        TaskUpdateDto updateDto2 =
                new TaskUpdateDto(null, null, null, newPriority);
        updatedTask = taskController.updateTask(user1Id, taskId, updateDto2);
        assertEquals(newPriority, updatedTask.getPriority().toString());

        // wrong taskId
        assertThrows(ConflictOnRequestException.class, () ->
                taskController.updateTask(user2Id, taskId, updateDto2));

        // wrong userId
        assertThrows(ConflictOnRequestException.class, () ->
                taskController.updateTask(user2Id, taskId, updateDto2));
    }

    @Test
    void shouldGetByAuthorId() {

        TaskFullDto task1 = taskController.addTask(user1Id, newTaskDto1);
        Long task1Id = task1.getId();
        TaskFullDto task2 = taskController.addTask(user1Id, newTaskDto2);
        Long task2Id = task2.getId();
        TaskFullDto task3 = taskController.addTask(user1Id, newTaskDto3);
        Long task3Id = task3.getId();
        TaskFullDto task4 = taskController.addTask(user1Id, newTaskDto4);
        Long task4Id = task4.getId();

        List<TaskShortDto> foundTasks
                = taskController.getByAuthorId(
                user1Id,
                null,
                null,
                "MID",
                0, 10, "status");

        assertEquals(2, foundTasks.size());
        assertEquals(newTaskDto1.getTitle(), foundTasks.get(0).getTitle());
        assertEquals(newTaskDto4.getTitle(), foundTasks.get(1).getTitle());

        foundTasks
                = taskController.getByAuthorId(
                user1Id,
                null,
                null,
                null,
                0, 10, "id");
        assertEquals(4, foundTasks.size());
    }
}