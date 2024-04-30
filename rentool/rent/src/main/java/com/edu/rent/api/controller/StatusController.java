package com.edu.rent.api.controller;

import com.edu.rent.api.mapper.StatusMapper;
import com.edu.rent.api.model.request.StatusRequest;
import com.edu.rent.model.Status;
import com.edu.rent.service.impl.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
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
@RequestMapping("api/v1/statuses")
public class StatusController {
    private final StatusService statusService;
    private final StatusMapper statusMapper;

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

    @Operation(summary = "Добавить статус")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус добавлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public void addStatus(@RequestBody StatusRequest status) {
        statusService.save(statusMapper.mapToItem(status));
    }

    @Operation(summary = "Удалить статус")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус удален")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable Long id) {
        statusService.delete(id);
    }

    @Operation(summary = "Обновить статус")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус обновлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Status updateStatus(
        @PathVariable Long id,
        @RequestBody StatusRequest status
    ) {
        return statusService.update(id, statusMapper.mapToItem(status));
    }
}
