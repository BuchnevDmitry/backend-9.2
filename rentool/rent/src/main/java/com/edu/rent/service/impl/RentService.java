package com.edu.rent.service.impl;

import com.edu.rent.api.mapper.RentMapper;
import com.edu.rent.model.Rent;
import com.edu.rent.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService{

    private final RentRepository rentRepository;
    private final StatusService statusService;
    private final RentMapper rentMapper;

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

    public Rent update(UUID rentId, Rent item, UUID userId) {
        Rent rent = getById(rentId);
        item.setId(rent.getId());
        item.setUserId(userId);
        rentMapper.updateRent(item, rent);
        return rentRepository.save(rent);
    }

    public Rent changeStatusOnCancel(UUID uuid) {
        Rent rent = getById(uuid);
        if (rent.getStatus().getId().equals((long) 1) || rent.getStatus().getId().equals((long) 2)) {
            rent.setStatus(statusService.getById((long) 5));
        } else {
            throw new RuntimeException("Невозможно изменить текущий статус в состояние отмены");
        }
        return rentRepository.save(rent);
    }

    public Rent changeStatusOnReturn(UUID uuid) {
        Rent rent = getById(uuid);
        if (rent.getStatus().getId().equals((long) 3)) {
            rent.setStatus(statusService.getById((long) 6));
        } else {
            throw new RuntimeException("Невозможно изменить текущий статус в состояние возврата");
        }
        return rentRepository.save(rent);
    }
}
