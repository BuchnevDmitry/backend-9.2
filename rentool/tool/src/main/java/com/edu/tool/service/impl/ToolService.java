package com.edu.tool.service.impl;

import com.edu.tool.api.mapper.ToolMapper;
import com.edu.tool.api.model.request.ToolQuantityUpdateRequest;
import com.edu.tool.common.ToolParam;
import com.edu.tool.exception.BadRequestException;
import com.edu.tool.exception.NotFoundException;
import com.edu.tool.model.Category;
import com.edu.tool.model.Tool;
import com.edu.tool.repository.CategoryRepository;
import com.edu.tool.repository.ToolRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import com.edu.tool.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;
    private final CategoryRepository categoryRepository;
    private final ToolMapper toolMapper;
    private final ImageService imageService;

    public List<Tool> getAllItems(PageRequest pageRequest) {
        return toolRepository.findAll(pageRequest).getContent();
    }

    public List<Tool> getAllByCategory(String category, PageRequest pageRequest) {
        List<Category> byName = categoryRepository.findAllByNameLikeIgnoreCase("%" + category + "%");
        return toolRepository.findAllByCategories(byName, pageRequest).getContent();
    }

    public List<Tool> getAllItems(List<UUID> listIds) {
        List<Tool> tools = listIds.stream().map(id -> getById(id)).collect(Collectors.toList());
        return tools;
    }

    public Tool getById(UUID uuid) {
        return toolRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Инструмент не найден"));
    }

    @Transactional
    public void delete(UUID uuid) {
        toolRepository.deleteById(uuid);
    }

    @Transactional
    public void save(Tool item, MultipartFile image) {
        String imageUrl = imageService.upload(image);
        item.setImageUrl(imageUrl);
        toolRepository.save(item);
    }

    @Transactional
    public Tool update(UUID uuid, Tool item) {
        Tool tool = getById(uuid);
        item.setId(uuid);
        toolMapper.updateTool(item, tool);
        return toolRepository.save(tool);
    }

    @Transactional
    public void updateToolQuantities(List<ToolQuantityUpdateRequest> toolUpdates, ToolParam operation) {
        List<UUID> ids = toolUpdates.stream().map(ToolQuantityUpdateRequest::id).toList();
        List<Tool> tools = toolRepository.findAllById(ids);
        if (tools.size() != toolUpdates.size()) {
            throw new BadRequestException("Некорректный запрос. Инструмента с таким id не существует");
        }
        Map<UUID, Integer> updateMap = toolUpdates.stream()
            .collect(Collectors.toMap(ToolQuantityUpdateRequest::id, ToolQuantityUpdateRequest::count));
        for (Tool tool : tools) {
            Integer count = updateMap.get(tool.getId());
            if (operation.equals(ToolParam.ADD)) {
                tool.setCount(tool.getCount() + count);
            } else if (operation.equals(ToolParam.SUBTRACT)) {
                Long diff = tool.getCount() - count;
                if (diff < 0) {
                    throw new BadRequestException("Некорректный запрос. Количество инструментов при вычитании не может быть меньше 0");
                }
                tool.setCount(tool.getCount() - count);
            }
        }

    }
}
