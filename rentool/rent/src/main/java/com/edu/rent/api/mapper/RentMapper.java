package com.edu.rent.api.mapper;

import com.edu.rent.api.model.request.RentRequest;
import com.edu.rent.model.Rent;
import com.edu.rent.service.impl.ReceivingService;
import com.edu.rent.service.impl.StatusService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RentMapper {
    protected StatusService statusService;
    protected ReceivingService receivingService;

    @Autowired
    protected void setRentMapper(
        StatusService statusService,
        ReceivingService receivingService
    ) {
        this.statusService = statusService;
        this.receivingService = receivingService;
    }

    @Mapping(target = "status", expression = "java(statusService.getById(rentRequest.statusId()))")
    @Mapping(target = "receivingMethod", expression = "java(receivingService.getById(rentRequest.receivingMethodId()))")
    public abstract Rent mapToItem(RentRequest rentRequest);
}
