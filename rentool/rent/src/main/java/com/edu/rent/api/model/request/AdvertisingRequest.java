package com.edu.rent.api.model.request;

import jakarta.validation.constraints.NotNull;

public record AdvertisingRequest(
        String name,
        @NotNull
        String imageUrl
) {
}
