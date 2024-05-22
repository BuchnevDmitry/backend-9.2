package com.edu.rent.api.model.request;

import com.edu.rent.model.RentTool;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

public record RentCreateRequest(
    @NotNull
    OffsetDateTime startDate,
    @NotNull
    OffsetDateTime endDate,
    @NotNull
    Long price,
    @NotNull
    Long timeReceivingId,
    @NotNull
    Long receivingMethodId,
    @NotEmpty
    String address,
    @NotNull
    List<RentTool> tools
) {
}
