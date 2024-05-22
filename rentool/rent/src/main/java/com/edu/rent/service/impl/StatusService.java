package com.edu.rent.service.impl;

import com.edu.rent.exception.NotFoundException;
import com.edu.rent.model.Status;
import com.edu.rent.repository.StatusRepository;
import com.edu.rent.service.CrudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService implements CrudService<Status, Long> {

    private final StatusRepository statusRepository;

    @Override
    public List<Status> getAllItems() {
        return statusRepository.findAll();
    }

    @Override
    public Status getById(Long id) {
        return statusRepository.findById(id).orElseThrow(() -> new NotFoundException("Статус не найден"));
    }

    @Override
    public void delete(Long id) {
        statusRepository.deleteById(id);
    }

    @Override
    public void save(Status item) {
        statusRepository.save(item);
    }

    @Override
    public Status update(Long id, Status item) {
        Status status = getById(id);
        status.setName(item.getName());
        return statusRepository.save(status);
    }
}
