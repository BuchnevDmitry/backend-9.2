package com.edu.tool.service.impl;

import com.edu.tool.api.mapper.ToolMapper;
import com.edu.tool.exception.NotFoundException;
import com.edu.tool.model.Tool;
import com.edu.tool.repository.ToolRepository;
import com.edu.tool.service.CrudService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToolService implements CrudService<Tool, UUID> {
    private final ToolRepository toolRepository;
    private final ToolMapper toolMapper;

    @Override
    public List<Tool> getAllItems(PageRequest pageRequest) {
        return toolRepository.findAll(pageRequest).getContent();
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
