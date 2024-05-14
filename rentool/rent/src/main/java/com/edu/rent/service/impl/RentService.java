package com.edu.rent.service.impl;

import com.edu.rent.model.Rent;
import com.edu.rent.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentService{

    private final RentRepository rentRepository;
    public List<Rent> getAllByUser(UUID uuid, PageRequest pageRequest) {
        return rentRepository.findAllByUserId(uuid, pageRequest).getContent();
    }

    public List<Rent> getAllItems(PageRequest pageRequest) {
        return rentRepository.findAll(pageRequest).getContent();
    }

    public Rent getById(UUID uuid) {
        return rentRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Аренда не найдена"));
    }

    public void delete(UUID uuid) {
        rentRepository.deleteById(uuid);
    }

    public void save(Rent item, UUID userId) {
        item.setUserId(userId);
        rentRepository.save(item);
    }

    public Rent update(UUID uuid, Rent item) {
        Rent rent = getById(uuid);
        rent.setStatus(item.getStatus());
        return rentRepository.save(rent);
    }
}
