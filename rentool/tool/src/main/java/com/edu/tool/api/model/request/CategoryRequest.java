package com.edu.tool.api.model.request;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(
    @NotEmpty
    String name
) {
}
