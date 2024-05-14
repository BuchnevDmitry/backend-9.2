package com.edu.tool.service.impl;

import com.edu.tool.api.mapper.ToolMapper;
import com.edu.tool.exception.NotFoundException;
import com.edu.tool.model.Category;
import com.edu.tool.model.Tool;
import com.edu.tool.repository.CategoryRepository;
import com.edu.tool.repository.ToolRepository;
import com.edu.tool.service.CrudService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToolService implements CrudService<Tool, UUID> {
    private final ToolRepository toolRepository;
    private final CategoryRepository categoryRepository;
    private final ToolMapper toolMapper;

    @Override
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

    @Override
    public Tool getById(UUID uuid) {
        return toolRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Инструмент не найден"));
    }

    @Override
    public void delete(UUID uuid) {
        toolRepository.deleteById(uuid);
    }

    @Override
    public void save(Tool item) {
        toolRepository.save(item);
    }

    @Override
    public Tool update(UUID uuid, Tool item) {
        Tool tool = getById(uuid);
        item.setId(uuid);
        toolMapper.updateTool(item, tool);
        return toolRepository.save(tool);
    }
}
