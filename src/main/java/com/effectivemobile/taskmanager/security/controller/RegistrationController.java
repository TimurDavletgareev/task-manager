package com.effectivemobile.taskmanager.security.controller;


import com.effectivemobile.taskmanager.security.dto.ResponseMessage;
import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody NewUserDto newUserDto) {

        if (userService.addUser(newUserDto)) {
            return ResponseEntity.ok(new ResponseMessage("User CREATED"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("User NOT CREATED"));
        }
    }
}
