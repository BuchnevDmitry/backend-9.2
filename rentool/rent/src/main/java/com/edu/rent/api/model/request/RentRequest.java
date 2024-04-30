package com.edu.rent.api.model.request;

import com.edu.rent.model.RentTool;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record RentRequest(
    OffsetDateTime startDate,
    OffsetDateTime endDate,
    Long price,
    UUID userId,
    Long statusId,
    Long receivingMethodId,
    String address,
    List<RentTool> tools
) {
}
