package com.edu.rent.service.impl;

import com.edu.rent.model.Rent;
import com.edu.rent.repository.RentRepository;
import com.edu.rent.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentService implements CrudService<Rent, UUID> {

    private final RentRepository rentRepository;
    @Override
    public List<Rent> getAllItems() {
        return rentRepository.findAll();
    }

    @Override
    public Rent getById(UUID uuid) {
        return rentRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Аренда не найдена"));
    }

    @Override
    public void delete(UUID uuid) {
        rentRepository.deleteById(uuid);
    }

    @Override
    public void save(Rent item) {
        rentRepository.save(item);
    }

    @Override
    public Rent update(UUID uuid, Rent item) {
        Rent rent = getById(uuid);
        rent.setStatus(item.getStatus());
        return rentRepository.save(rent);
    }
}
