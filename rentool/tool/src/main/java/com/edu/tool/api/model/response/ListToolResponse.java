package com.edu.tool.api.model.response;

import com.edu.tool.model.Tool;
import java.util.List;

public record ListToolResponse(
    List<Tool> tools,
    Integer size
) {

}
