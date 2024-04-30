package com.edu.rent.api.mapper;

import com.edu.rent.api.model.request.StatusRequest;
import com.edu.rent.model.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status mapToItem(StatusRequest statusRequest);
}
