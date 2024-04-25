package com.edu.tool.api.model.request;

public record ToolRequest(
    String model,
    String description,
    String imageUrl,
    Long priceHour,
    Long count,
    Long brandId,
    Long categoryId
) {
}
