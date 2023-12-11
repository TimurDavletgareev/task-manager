package com.effectivemobile.taskmanager.security.controller;

import com.effectivemobile.taskmanager.security.dto.JwtResponseDto;
import com.effectivemobile.taskmanager.security.dto.LoginRequestDto;
import com.effectivemobile.taskmanager.security.jwt.JwtUtils;
import com.effectivemobile.taskmanager.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(value = "Авторизация", description = "API для авторизации пользователя")
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @ApiOperation(value = "Авторизация пользователя")
    @PostMapping
    public ResponseEntity<?> authUser(@RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponseDto(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail()));
    }
}
