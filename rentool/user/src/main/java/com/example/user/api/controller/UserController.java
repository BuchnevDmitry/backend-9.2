package com.example.user.api.controller;

import com.example.user.api.model.request.UserRequest;
import com.example.user.api.model.response.ListUserResponse;
import com.example.user.model.User;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Зарегистрировать пользователя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Пользователь зарегистрирован")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public void addUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
    }

    @Operation(summary = "Получить всех пользователей")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все пользователи получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ListUserResponse getUsers(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<User> users = userService.getUsers(PageRequest.of(page, size));
        return new ListUserResponse(users, users.size());
    }

    @Operation(summary = "Получить пользователя по id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Пользователь по id получен")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUser(
        @PathVariable @NotNull UUID id
    ) {
        return userService.getUser(id);
    }

}
