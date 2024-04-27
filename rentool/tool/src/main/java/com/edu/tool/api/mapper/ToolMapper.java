package com.edu.tool.api.mapper;

import com.edu.tool.api.model.request.ToolRequest;
import com.edu.tool.model.Tool;
import com.edu.tool.service.impl.BrandService;
import com.edu.tool.service.impl.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ToolMapper {
    protected BrandService brandService;
    protected CategoryService categoryService;

    @Autowired
    protected void setToolMapper(
        BrandService brandService,
        CategoryService categoryService
    ) {
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @Mapping(target = "brand", expression = "java(brandService.getById(toolRequest.brandId()))")
    @Mapping(target = "category", expression = "java(categoryService.getById(toolRequest.categoryId()))")
    public abstract Tool mapToItem(ToolRequest toolRequest);

    public abstract void updateTool(Tool source, @MappingTarget Tool target);
}
