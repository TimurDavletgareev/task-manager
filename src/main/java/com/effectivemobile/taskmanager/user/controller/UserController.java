package com.effectivemobile.taskmanager.user.controller;

import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.dto.UserFullDto;
import com.effectivemobile.taskmanager.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "Пользователи", description = "API для работы с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Обновление данных пользователя")
    @PatchMapping
    public UserFullDto updateUser(@RequestHeader("X-Owner-User-Id") Long userId,
                                  @RequestBody @Valid NewUserDto newUserDto) {

        return userService.updateUser(userId, newUserDto);
    }

    @ApiOperation(value = "Получение пользователя по id")
    @GetMapping("/{userId}")
    public UserFullDto getById(@PathVariable @NotNull Long userId) {

        return userService.getById(userId);
    }

    @ApiOperation(value = "Получение пользователя по name")
    @GetMapping
    public UserFullDto getByUsername(@RequestParam(value = "name") String name) {

        return userService.getByName(name);
    }

    @ApiOperation(value = "удаление пользователя по id")
    @DeleteMapping("/{userId}")
    public boolean removeById(@RequestHeader("X-Owner-User-Id") Long userId) {

        return userService.removeById(userId);
    }
}
