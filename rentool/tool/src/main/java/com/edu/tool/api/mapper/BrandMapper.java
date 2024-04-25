package com.edu.tool.api.mapper;

import com.edu.tool.api.model.request.BrandRequest;
import com.edu.tool.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand mapToItem(BrandRequest categoryRequest);
}
