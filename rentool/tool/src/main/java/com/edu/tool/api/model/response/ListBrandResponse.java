package com.edu.tool.api.model.response;

import com.edu.tool.model.Brand;
import java.util.List;

public record ListBrandResponse(
    List<Brand> brands,
    Integer size
) {
}
