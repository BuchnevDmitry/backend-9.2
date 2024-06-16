package com.edu.rent.api.mapper;

import com.edu.rent.api.model.request.AdvertisingRequest;
import com.edu.rent.model.Advertising;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdvertisingMapper {
    Advertising mapToItem(AdvertisingRequest request);

}
