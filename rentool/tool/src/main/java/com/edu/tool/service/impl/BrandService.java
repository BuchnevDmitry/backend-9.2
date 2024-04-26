package com.edu.tool.service.impl;

import com.edu.tool.exception.NotFoundException;
import com.edu.tool.model.Brand;
import com.edu.tool.repository.BrandRepository;
import com.edu.tool.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements CrudService<Brand, Long> {
    private final BrandRepository brandRepository;
    public List<Brand> getAllItems(PageRequest pageRequest) {
        return brandRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Бренд не найден"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Brand item) {
        brandRepository.save(item);
    }

    @Override
    @Transactional
    public Brand update(Long id, Brand item) {
        Brand brand = getById(id);
        brand.setName(item.getName());
        return brandRepository.save(brand);
    }
}
