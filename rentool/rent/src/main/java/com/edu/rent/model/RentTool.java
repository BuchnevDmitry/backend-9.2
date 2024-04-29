package com.edu.rent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class RentTool {
    @Column(name = "tool_id")
    private UUID toolId;

    @Column(name = "count_tool")
    private Long countTool;
}
