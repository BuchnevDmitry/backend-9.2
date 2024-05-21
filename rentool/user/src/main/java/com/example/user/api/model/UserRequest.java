package com.example.user.api.model;

public record UserRequest(
    String login,
    String password,
    String firstName,
    String lastName,
    String phone,
    String email
) {
}
