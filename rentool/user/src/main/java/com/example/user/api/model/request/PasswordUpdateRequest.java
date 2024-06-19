package com.example.user.api.model.request;

import jakarta.validation.constraints.NotEmpty;

public record PasswordUpdateRequest(
    @NotEmpty
    String password
) {
}
