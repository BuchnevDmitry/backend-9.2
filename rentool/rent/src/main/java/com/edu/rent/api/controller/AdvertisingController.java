package com.edu.rent.api.controller;

import com.edu.rent.api.mapper.AdvertisingMapper;
import com.edu.rent.api.model.request.AdvertisingRequest;
import com.edu.rent.model.Advertising;
import com.edu.rent.service.impl.AdvertisingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
@RequestMapping("api/v1/advertising/")
public class AdvertisingController {

    private final AdvertisingService advertisingService;
    private final AdvertisingMapper advertisingMapper;
    @Operation(summary = "Получить все рекламные баннеры")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все рекламные баннеры получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<Advertising> getAdvertisings(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Advertising> ads = advertisingService.getAllItems(PageRequest.of(page, size));
        return ads;
    }

    @Operation(summary = "Получить рекламный баннер")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рекламный баннер получен")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Advertising getAdvertising(@PathVariable Long id) {
        return advertisingService.getById(id);
    }

    @Operation(summary = "Добавить рекламный баннер")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рекламный баннер добавлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('admin')")
    public void addAdvertising(
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart("ad") @Valid AdvertisingRequest advertising
    ) {
        advertisingService.save(advertisingMapper.mapToItem(advertising), image);
    }

    @Operation(summary = "Удалить рекламный баннер")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рекламный баннер удален")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public void deleteAdvertising(@PathVariable Long id) {
        advertisingService.delete(id);
    }

    @Operation(summary = "Обновить рекламный баннер")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рекламный баннер обновлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public Advertising updateAdvertising(
            @PathVariable Long id,
            @RequestBody @Valid AdvertisingRequest advertising
    ) {
        return advertisingService.update(id, advertisingMapper.mapToItem(advertising));
    }
}
