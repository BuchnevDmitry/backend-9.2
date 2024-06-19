package com.example.user.api.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record PasswordUpdateRequest(
    @NotEmpty
    @Min(1)
    @Max(100)
    String password
) {
}
