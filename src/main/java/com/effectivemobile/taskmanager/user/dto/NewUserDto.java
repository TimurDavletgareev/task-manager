package com.effectivemobile.taskmanager.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @NotBlank
    @Size(min = 2, message = "size must be between 2 and 20")
    @Size(max = 20, message = "size must be between 2 and 20")
    private String name;

    @NotNull
    @NotBlank
    @Email
    @Size(min = 6, message = "size must be between 6 and 50")
    @Size(max = 50, message = "size must be between 6 and 50")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, message = "size must be between 6 and 15")
    @Size(max = 15, message = "size must be between 6 and 15")
    private String password;

    @NotNull
    private String passwordConfirm;
}
