package com.example.user.api.controller;

import com.example.user.api.model.request.PasswordUpdateRequest;
import com.example.user.api.model.request.UserRequest;
import com.example.user.api.model.response.ListUserResponse;
import com.example.user.model.User;
import com.example.user.parser.JwtParser;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@SecurityRequirement(name = "Keycloak")
public class UserController {

    private final UserService userService;
    private final JwtParser jwtParser;

    @Operation(summary = "Зарегистрировать пользователя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Пользователь зарегистрирован")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public void addUser(@RequestBody @Valid UserRequest userRequest) {
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
    @PreAuthorize("hasRole('admin')")
    public ListUserResponse getUsers(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<User> users = userService.getUsers(PageRequest.of(page, size));
        return new ListUserResponse(users, users.size());
    }

    @Operation(summary = "Получить информацию о профиле пользователя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Информация получена")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')")
    public User getUser(
        @AuthenticationPrincipal Jwt jwt
    ) {
        return userService.getUser(jwtParser.getIdFromToken(jwt));
    }

    @Operation(summary = "Изменить пароль")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Пароль изменён")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('user')")
    public void changePassword(
        @RequestBody @Valid PasswordUpdateRequest request,
        @AuthenticationPrincipal Jwt jwt
    ) {
        userService.changePassword(request, jwtParser.getIdFromToken(jwt));
    }

}
