package com.edu.rent.api.mapper;

import com.edu.rent.api.model.request.RentCreateRequest;
import com.edu.rent.api.model.request.RentUpdateRequest;
import com.edu.rent.model.Rent;
import com.edu.rent.service.impl.ReceivingService;
import com.edu.rent.service.impl.StatusService;
import com.edu.rent.service.impl.TimeReceivingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RentMapper {
    protected StatusService statusService;
    protected ReceivingService receivingService;
    protected TimeReceivingService timeReceivingService;

    @Autowired
    protected void setRentMapper(
        StatusService statusService,
        ReceivingService receivingService,
        TimeReceivingService timeReceivingService
    ) {
        this.statusService = statusService;
        this.receivingService = receivingService;
        this.timeReceivingService = timeReceivingService;
    }

    @Mapping(target = "status", expression = "java(statusService.getById((long) 1))")
    @Mapping(target = "receivingMethod", expression = "java(receivingService.getById(rentRequest.receivingMethodId()))")
    @Mapping(target = "timeReceiving", expression = "java(timeReceivingService.getById(rentRequest.timeReceivingId()))")
    public abstract Rent mapCreateRequestToItem(RentCreateRequest rentRequest);

    @Mapping(target = "status", expression = "java(statusService.getById(rentRequest.statusId()))")
    @Mapping(target = "receivingMethod", expression = "java(receivingService.getById(rentRequest.receivingMethodId()))")
    @Mapping(target = "timeReceiving", expression = "java(timeReceivingService.getById(rentRequest.timeReceivingId()))")
    public abstract Rent mapUpdateRequestToItem(RentUpdateRequest rentRequest);
}
