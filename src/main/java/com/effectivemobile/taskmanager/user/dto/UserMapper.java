package com.effectivemobile.taskmanager.user.dto;

import com.effectivemobile.taskmanager.user.model.User;

public class UserMapper {

    public static UserFullDto modelToFullDto(User user) {

        return new UserFullDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto modelToShortDto(User user) {

        return new UserShortDto(user.getId(), user.getName());
    }
}
