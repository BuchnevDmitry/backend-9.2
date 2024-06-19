package com.example.user.api.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
    @NotEmpty
    @Min(1)
    @Max(100)
    String login,
    @NotEmpty
    @Min(1)
    @Max(100)
    String password,
    @NotEmpty
    @Min(1)
    @Max(100)
    String firstName,
    @NotEmpty
    @Min(1)
    @Max(100)
    String lastName,
    @NotEmpty
    String phone,
    @NotEmpty
    String email
) {
}
