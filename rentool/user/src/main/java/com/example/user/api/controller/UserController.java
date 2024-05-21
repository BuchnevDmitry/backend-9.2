package com.example.user.api.controller;

import com.example.user.api.model.UserRequest;
import com.example.user.service.KeycloakService;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public void addUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
    }
}
