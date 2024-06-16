package com.edu.rent.api.model.request;

import com.edu.rent.model.RentTool;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

public record RentCostRequest(
    @NotNull
    OffsetDateTime startDate,
    @NotNull
    OffsetDateTime endDate,
    @NotNull
    List<RentTool> tools
) {
}
