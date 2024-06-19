package com.example.user.api.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
    @NotEmpty
    String login,
    @NotEmpty
    String password,
    @NotEmpty
    String firstName,
    @NotEmpty
    String lastName,
    @NotEmpty
    String phone,
    @NotEmpty
    String email
) {
}
