package com.edu.rent.api.model.response;

import java.util.UUID;

public record ToolResponse(
    UUID id,
    Long priceDay
) {
}
