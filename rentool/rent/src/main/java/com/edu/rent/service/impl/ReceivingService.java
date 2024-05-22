package com.edu.rent.service.impl;

import com.edu.rent.exception.NotFoundException;
import com.edu.rent.model.ReceivingMethod;
import com.edu.rent.repository.ReceivingRepository;
import com.edu.rent.service.CrudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivingService implements CrudService<ReceivingMethod, Long> {

    private final ReceivingRepository receivingRepository;

    @Override
    public List<ReceivingMethod> getAllItems() {
        return receivingRepository.findAll();
    }

    @Override
    public ReceivingMethod getById(Long id) {
        return receivingRepository.findById(id).orElseThrow(() -> new NotFoundException("Способ получения не найден"));
    }

    @Override
    public void delete(Long id) {
        receivingRepository.deleteById(id);
    }

    @Override
    public void save(ReceivingMethod item) {
        receivingRepository.save(item);
    }

    @Override
    public ReceivingMethod update(Long id, ReceivingMethod item) {
        ReceivingMethod receivingMethod = getById(id);
        receivingMethod.setName(item.getName());
        return receivingRepository.save(receivingMethod);
    }
}
