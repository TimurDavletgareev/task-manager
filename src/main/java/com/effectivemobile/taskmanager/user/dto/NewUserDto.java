package com.effectivemobile.taskmanager.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUserDto {

    @NotBlank
    @Size(min = 2, message = "size must be between 2 and 20")
    @Size(max = 20, message = "size must be between 2 and 20")
    private final String name;

    @NotBlank
    @Email
    @Size(min = 6, message = "size must be between 6 and 50")
    @Size(max = 50, message = "size must be between 6 and 50")
    private final String email;

    @NotBlank
    @Size(min = 6, message = "size must be between 6 and 15")
    @Size(max = 15, message = "size must be between 6 and 15")
    private final String password;

    private final String passwordConfirm;
}
