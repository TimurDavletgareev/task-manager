package com.effectivemobile.taskmanager.user.service;

import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.dto.UserFullDto;

public interface UserService {

    boolean addUser(NewUserDto newUserDto);

    UserFullDto updateUser(Long userId, NewUserDto newUserDto);

    UserFullDto getById(Long userId);

    UserFullDto getByName(String name);

    boolean removeById(Long userId);
}
