package com.edu.tool.api.contoller;

import com.edu.tool.api.mapper.ToolMapper;
import com.edu.tool.api.model.request.ToolQuantityUpdateRequest;
import com.edu.tool.api.model.request.ToolRequest;
import com.edu.tool.api.model.response.ListToolResponse;
import com.edu.tool.common.ToolParam;
import com.edu.tool.model.Tool;
import com.edu.tool.service.impl.ToolService;
import com.edu.tool.common.ToolSort;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tools")
@SecurityRequirement(name = "Keycloak")
@Validated
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
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('admin')")
    public void addTool(
        @RequestPart(value = "image") MultipartFile image,
        @RequestPart("tool") ToolRequest tool
    ) {
        toolService.save(toolMapper.mapToItem(tool), image);
    }

    @Operation(summary = "Удалить инструмент")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Инструмент удален")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
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
    @PreAuthorize("hasRole('admin')")
    public Tool updateTool(
        @PathVariable @NotNull UUID id,
        @RequestBody @Valid ToolRequest tool
    ) {
        return toolService.update(id, toolMapper.mapToItem(tool));
    }

    @Hidden
    @PatchMapping("/update-quantities")
    public void updateToolQuantities(
        @RequestBody @Valid List<ToolQuantityUpdateRequest> toolUpdates,
        @RequestParam(required = false) ToolParam param
    ) {
        toolService.updateToolQuantities(toolUpdates, param);
    }
}
