package com.edu.rent.api.controller;

import com.edu.rent.model.Status;
import com.edu.rent.service.impl.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
@RequestMapping("api/v1/statuses")
public class StatusController {
    private final StatusService statusService;

    @Operation(summary = "Получить все статусы")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все статусы получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<Status> getStatuses() {
        List<Status> brands = statusService.getAllItems();
        return brands;
    }

    @Operation(summary = "Получить статус")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус получен")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Status getStatus(@PathVariable Long id) {
        return statusService.getById(id);
    }

}
