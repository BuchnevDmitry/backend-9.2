package com.edu.tool.api.mapper;

import com.edu.tool.api.model.request.CategoryRequest;
import com.edu.tool.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapToItem(CategoryRequest categoryRequest);
}
