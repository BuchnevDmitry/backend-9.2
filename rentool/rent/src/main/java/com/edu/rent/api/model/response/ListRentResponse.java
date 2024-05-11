package com.edu.rent.api.model.response;

import com.edu.rent.model.Rent;
import java.util.List;

public record ListRentResponse(
    List<Rent> rents,
    int size
) {
}
