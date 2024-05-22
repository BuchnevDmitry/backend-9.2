package com.edu.rent.access;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Access {
    STATUS_CANCEL(List.of("AWAITING_CONFIRMATION", "AWAITING_RECEIPT")),
    STATUS_RETURN(List.of("RECEIVED"));
    private final List value;
}
