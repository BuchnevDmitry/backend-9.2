package com.edu.tool.service.impl;

import com.edu.tool.exception.NotFoundException;
import com.edu.tool.model.Category;
import com.edu.tool.repository.CategoryRepository;
import com.edu.tool.service.CrudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CrudService<Category, Long> {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllItems(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Категория не найдена"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Category item) {
        categoryRepository.save(item);
    }

    @Override
    @Transactional
    public Category update(Long id, Category item) {
        Category category = getById(id);
        category.setName(item.getName());
        return categoryRepository.save(category);
    }
}
