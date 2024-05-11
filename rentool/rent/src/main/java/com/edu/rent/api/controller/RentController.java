package com.edu.rent.api.controller;

import com.edu.rent.api.mapper.RentMapper;
import com.edu.rent.api.model.request.RentRequest;
import com.edu.rent.model.Rent;
import com.edu.rent.service.impl.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rents")
public class RentController {

    private final RentService rentService;
    private final RentMapper rentMapper;

    @Operation(summary = "Получить все аренды")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все аренды получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<Rent> getRents() {
        List<Rent> rents = rentService.getAllItems();
        return rents;
    }

    @Operation(summary = "Получить аренду")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Аренда получена")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Rent getRent(@PathVariable UUID id) {
        return rentService.getById(id);
    }

    @Operation(summary = "Добавить аренду")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Аренда добавлена")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public void addRent(@RequestBody RentRequest rent) {
        rentService.save(rentMapper.mapToItem(rent));
    }

    @Operation(summary = "Удалить аренду")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Аренда удалена")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable UUID id) {
        rentService.delete(id);
    }

    @Operation(summary = "Обновить аренду")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Аренда обновлена")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Rent updateRent(
        @PathVariable UUID id,
        @RequestBody RentRequest rent
    ) {
        return rentService.update(id, rentMapper.mapToItem(rent));
    }
}