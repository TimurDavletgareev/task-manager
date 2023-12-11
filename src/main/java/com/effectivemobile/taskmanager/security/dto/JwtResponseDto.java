package com.effectivemobile.taskmanager.security.dto;

import lombok.Data;

@Data
public class JwtResponseDto {

    private final String token;
    private final String type = "Bearer";
    private final Long id;
    private final String username;
    private final String email;
}
