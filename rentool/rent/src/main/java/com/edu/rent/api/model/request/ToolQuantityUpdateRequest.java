package com.edu.rent.api.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ToolQuantityUpdateRequest(
    @NotNull
    UUID id,
    @Min(1)
    @Max(10000)
    Long count
) {

}
