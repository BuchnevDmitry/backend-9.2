package com.edu.rent.api.controller;

import com.edu.rent.model.TimeReceiving;
import com.edu.rent.service.impl.TimeReceivingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
@RequestMapping("api/v1/time-receiving/")
public class TimeReceivingController {

    private final TimeReceivingService timeReceivingService;

    @Operation(summary = "Получить все временные промежутки получения заказа")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все временные промежутки заказа получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<TimeReceiving> getMethods() {
        return timeReceivingService.getAllItems();
    }
}
