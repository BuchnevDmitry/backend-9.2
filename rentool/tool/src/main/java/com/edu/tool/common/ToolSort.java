package com.edu.tool.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ToolSort {
    PRICE_ASC(Sort.by(Sort.Direction.ASC, "priceDay")),
    PRICE_DESC(Sort.by(Sort.Direction.DESC, "priceDay"));
    private final Sort sortValue;
}
