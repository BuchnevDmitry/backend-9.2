package com.edu.rent.api.model.request;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

public record RentExtendRequest(
    @NotNull
    OffsetDateTime endDate
) {
}
