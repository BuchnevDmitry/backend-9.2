package com.edu.tool.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ToolParam {
    ADD("add"),
    SUBTRACT("subtract");
    private final String value;
}
