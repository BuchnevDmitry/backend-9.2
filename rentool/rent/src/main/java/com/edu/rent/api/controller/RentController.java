package com.edu.rent.api.controller;

import com.edu.rent.api.mapper.RentMapper;
import com.edu.rent.api.model.request.RentCostRequest;
import com.edu.rent.api.model.request.RentCreateRequest;
import com.edu.rent.api.model.request.RentExtendRequest;
import com.edu.rent.api.model.request.RentUpdateRequest;
import com.edu.rent.api.model.response.ListRentResponse;
import com.edu.rent.model.Rent;
import com.edu.rent.model.RentTool;
import com.edu.rent.parser.JwtParser;
import com.edu.rent.service.impl.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/rents")
public class RentController {

    private final RentService rentService;
    private final RentMapper rentMapper;
    private final JwtParser jwtParser;

    @Operation(summary = "Получить все аренды")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все аренды получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ListRentResponse getRents(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Rent> rents = rentService.getAllItems(PageRequest.of(page, size));
        return new ListRentResponse(rents, rents.size());
    }

    @Operation(summary = "Получить все аренды пользователя")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все аренды пользователя получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    public ListRentResponse getRentsByUser(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestHeader("Authorization") String token
    ) {
        UUID userId = jwtParser.getIdFromAccessToken(token);
        log.info("userId: {}", userId);
        List<Rent> rents = rentService.getAllByUser(userId, PageRequest.of(page, size));
        return new ListRentResponse(rents, rents.size());
    }

    @Operation(summary = "Получить аренду")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Аренда получена")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Rent getRent(@PathVariable @NotNull UUID id) {
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
    public Rent addRent(
        @RequestBody @Valid RentCreateRequest rent,
        @RequestHeader("Authorization") String token
    ) {
        UUID userId = jwtParser.getIdFromAccessToken(token);
        log.info("userId: {}", userId);
        return rentService.save(rentMapper.mapCreateRequestToItem(rent), userId);
    }

    @Operation(summary = "Удалить аренду")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Аренда удалена")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable @NotNull UUID id) {
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
        @PathVariable @NotNull UUID id,
        @RequestBody @Valid RentUpdateRequest rent
    ) {
        return rentService.update(id, rentMapper.mapUpdateRequestToItem(rent));
    }

    @Operation(summary = "Изменить стастус аренды в состояние отмены")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус изменён")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/cancel")
    public Rent changeStatusOnCancel(
        @PathVariable @NotNull UUID id,
        @RequestHeader("Authorization") String token
    ) {
        UUID userId = jwtParser.getIdFromAccessToken(token);
        return rentService.changeStatusOnCancel(id, userId);
    }

    @Operation(summary = "Изменить стастус аренды в состояние возврата")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус изменён")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/return")
    public Rent changeStatusOnReturn(
        @PathVariable @NotNull UUID id,
        @RequestHeader("Authorization") String token
    ) {
        UUID userId = jwtParser.getIdFromAccessToken(token);
        return rentService.changeStatusOnReturn(id, userId);
    }

    @Operation(summary = "Изменить стастус аренды в состояние продления")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Статус изменён")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/extend")
    public Rent changeStatusOnExtend(
        @PathVariable @NotNull UUID id,
        @RequestBody RentExtendRequest request,
        @RequestHeader("Authorization") String token
    ) {
        UUID userId = jwtParser.getIdFromAccessToken(token);
        return rentService.changeStatusOnExtend(id, request, userId);
    }

    @Operation(summary = "Изменить стастус аренды в состояние завершение")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Статус изменён")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/complete")
    public Rent changeStatusOnComplete(
            @PathVariable @NotNull UUID id
    ) {
        return rentService.changeStatusOnComplete(id);
    }



    @Operation(summary = "Получить итоговую стоимость аренды")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Итоговая стоимость получена")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/calculation-cost")
    public Long getTools(
        @RequestBody @NotNull RentCostRequest request
    ) {
        return rentService.calculationCost(request);
    }
}

