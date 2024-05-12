package com.edu.tool.api.contoller;

import com.edu.tool.api.mapper.ToolMapper;
import com.edu.tool.api.model.request.ToolRequest;
import com.edu.tool.api.model.response.ListToolResponse;
import com.edu.tool.model.Tool;
import com.edu.tool.service.impl.ToolService;
import com.edu.tool.sort.ToolSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("api/v1/tools")
public class ToolController {
    private final ToolService toolService;
    private final ToolMapper toolMapper;

    @Operation(summary = "Получить все иструменты")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все иструменты получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ListToolResponse getTools(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "PRICE_ASC") ToolSort sortParam
    ) {
        List<Tool> tools = toolService.getAllItems(PageRequest.of(page, size, sortParam.getSortValue()));
        return new ListToolResponse(tools, tools.size());
    }

    @Operation(summary = "Получить все иструменты по категории")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все иструменты по категории получены ")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public ListToolResponse getToolsByCategory(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = true) String category

        ) {
        List<Tool> tools = toolService.getAllByCategory(category, PageRequest.of(page, size));
        return new ListToolResponse(tools, tools.size());
    }

    @Operation(summary = "Получить все иструменты по списку id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все иструменты получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/by-ids")
    public ListToolResponse getTools(
        @RequestParam @NotNull List<UUID> listIds
    ) {
        List<Tool> tools = toolService.getAllItems(listIds);
        return new ListToolResponse(tools, tools.size());
    }

    @Operation(summary = "Получить инструмент")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Инструмент получен")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Tool getTool(@PathVariable @NotNull UUID id) {
        return toolService.getById(id);
    }

    @Operation(summary = "Добавить инструмент")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Инструмент добавлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public void addTool(@RequestBody @Valid ToolRequest category) {
        toolService.save(toolMapper.mapToItem(category));
    }

    @Operation(summary = "Удалить инструмент")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Инструмент удален")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable @NotNull UUID id) {
        toolService.delete(id);
    }

    @Operation(summary = "Обновить инструмент")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Инструмент обновлен")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Tool updateTool(
        @PathVariable @NotNull UUID id,
        @RequestBody @Valid ToolRequest category
    ) {
        return toolService.update(id, toolMapper.mapToItem(category));
    }
}
