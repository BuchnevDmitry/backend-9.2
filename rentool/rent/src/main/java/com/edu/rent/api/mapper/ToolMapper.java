package com.edu.rent.api.mapper;

import com.edu.rent.api.model.request.ToolQuantityUpdateRequest;
import com.edu.rent.model.Rent;
import com.edu.rent.model.RentTool;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ToolMapper {

    @Mapping(target = "id", expression = "java(rentTool.getToolId())")
    @Mapping(target = "count", expression = "java(rentTool.getCountTool())")
    public abstract ToolQuantityUpdateRequest mapRentToToolQuantity(RentTool rentTool);

    public abstract List<ToolQuantityUpdateRequest> mapRentToToolQuantity(Set<RentTool> rentTool);

}
