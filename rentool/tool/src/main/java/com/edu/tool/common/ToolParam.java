package com.edu.tool.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToolParam {
    ADD("add"),
    SUBTRACT("subtract");
    private final String value;
}
