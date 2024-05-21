package com.example.user.api.model.request;

public record UserRequest(
    String login,
    String password,
    String firstName,
    String lastName,
    String phone,
    String email
) {
}
