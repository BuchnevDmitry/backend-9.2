package com.edu.rent.api.model.request;

import com.edu.rent.model.RentTool;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record RentRequest(
    @NotNull
    OffsetDateTime startDate,
    @NotNull
    OffsetDateTime endDate,
    @NotNull
    Long price,
    @NotNull
    Long statusId,
    @NotNull
    Long receivingMethodId,
    @NotEmpty
    String address,
    @NotNull
    List<RentTool> tools
) {
}
