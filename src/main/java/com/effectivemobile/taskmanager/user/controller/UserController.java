package com.effectivemobile.taskmanager.user.controller;

import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.dto.UserFullDto;
import com.effectivemobile.taskmanager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public UserFullDto updateUser(@RequestHeader("X-Owner-User-Id") Long userId,
                                  @RequestBody @Valid NewUserDto newUserDto) {

        return userService.updateUser(userId, newUserDto);
    }

    @GetMapping("/{userId}")
    public UserFullDto getById(@PathVariable @NotNull Long userId) {

        return userService.getById(userId);
    }

    @GetMapping
    public UserFullDto getByUsername(@RequestParam(value = "name") String name) {

        return userService.getByName(name);
    }

    @DeleteMapping("/{userId}")
    public boolean removeById(@RequestHeader("X-Owner-User-Id") Long userId) {

        return userService.removeById(userId);
    }
}
