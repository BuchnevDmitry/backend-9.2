package com.edu.tool.api.contoller;

import com.edu.tool.api.mapper.BrandMapper;
import com.edu.tool.api.model.request.BrandRequest;
import com.edu.tool.api.model.response.ListBrandResponse;
import com.edu.tool.model.Brand;
import com.edu.tool.service.impl.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/brands")
@SecurityRequirement(name = "Keycloak")
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @Operation(summary = "Получить все бренды")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все бренды получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ListBrandResponse getBrands(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Brand> brands = brandService.getAllItems(PageRequest.of(page, size));
        return new ListBrandResponse(brands, brands.size());
    }

    @Operation(summary = "Получить бренд")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Бренд получен")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Brand getBrand(@PathVariable Long id) {
        return brandService.getById(id);
    }

    @Operation(summary = "Добавить бренд")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Бренд добавлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    @PreAuthorize("hasRole('admin')")
    public void addBrand(@RequestBody @Valid BrandRequest brand) {
        brandService.save(brandMapper.mapToItem(brand));
    }

    @Operation(summary = "Удалить бренд")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Бренд удален")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public void deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
    }

    @Operation(summary = "Обновить бренд")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Бренд обновлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public Brand updateBrand(
        @PathVariable Long id,
        @RequestBody @Valid BrandRequest brand
    ) {
        return brandService.update(id, brandMapper.mapToItem(brand));
    }
}
