package com.edu.tool.api.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ToolRequest(
    @NotEmpty
    String model,
    @NotEmpty
    String description,
    @NotNull
    Long priceDay,
    @NotNull
    Long count,
    @NotNull
    Long brandId,
    @NotNull
    Long categoryId
) {
}
