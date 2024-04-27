package com.edu.tool.api.contoller;

import com.edu.tool.api.mapper.CategoryMapper;
import com.edu.tool.api.model.request.CategoryRequest;
import com.edu.tool.api.model.response.ListCategoryResponse;
import com.edu.tool.model.Category;
import com.edu.tool.service.impl.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Получить все категории")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все категории получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ListCategoryResponse getCategories(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Category> categories = categoryService.getAllItems(PageRequest.of(page, size));
        return new ListCategoryResponse(categories, categories.size());
    }

    @Operation(summary = "Получить категорию")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Категория получена")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Добавить категорию")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Категория добавлена")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public void addCategory(@RequestBody @Valid CategoryRequest category) {
        categoryService.save(categoryMapper.mapToItem(category));
    }

    @Operation(summary = "Удалить категорию")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Категория удалена")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @Operation(summary = "Обновить категорию")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Категория обновлена")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Category updateCategory(
        @PathVariable Long id,
        @RequestBody @Valid CategoryRequest category
    ) {
        return categoryService.update(id, categoryMapper.mapToItem(category));
    }
}
