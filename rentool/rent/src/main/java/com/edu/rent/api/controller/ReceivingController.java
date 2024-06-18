package com.edu.rent.api.controller;

import com.edu.rent.model.ReceivingMethod;
import com.edu.rent.service.impl.ReceivingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
@RequestMapping("api/v1/receiving-methods/")
public class ReceivingController {
    private final ReceivingService receivingService;

    @Operation(summary = "Получить все методы получения заказа")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все методы получения заказа получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<ReceivingMethod> getMethods() {
        List<ReceivingMethod> receivingMethods = receivingService.getAllItems();
        return receivingMethods;
    }

}
