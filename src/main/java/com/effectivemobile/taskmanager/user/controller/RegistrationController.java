package com.effectivemobile.taskmanager.user.controller;


import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.dto.UserFullDto;
import com.effectivemobile.taskmanager.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Регистрация пользователя")
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @ApiOperation(value = "Добавление нового пользователя")
    @PostMapping
    public UserFullDto registerUser(@RequestBody NewUserDto newUserDto) {

        return userService.addUser(newUserDto);
    }
}
