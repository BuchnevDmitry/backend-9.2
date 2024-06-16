package com.edu.rent.api.model.response;

import java.util.List;

public record ListToolResponse(
    List<ToolResponse> tools,
    Integer size
) {

}
