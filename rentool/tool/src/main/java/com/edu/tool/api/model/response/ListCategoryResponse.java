package com.edu.tool.api.model.response;

import com.edu.tool.model.Category;
import java.util.List;

public record ListCategoryResponse(
    List<Category> categories,
    Integer size
) {
}
