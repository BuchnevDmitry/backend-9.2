package com.edu.rent.service.impl;

import com.edu.rent.access.Access;
import com.edu.rent.api.mapper.RentMapper;
import com.edu.rent.exception.AccessDeniedException;
import com.edu.rent.exception.BadRequestException;
import com.edu.rent.exception.NotFoundException;
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
    private final StatusService statusService;
    private final RentMapper rentMapper;

    public List<Rent> getAllByUser(UUID uuid, PageRequest pageRequest) {
        return rentRepository.findAllByUserId(uuid, pageRequest).getContent();
    }

    public List<Rent> getAllItems(PageRequest pageRequest) {
        return rentRepository.findAll(pageRequest).getContent();
    }

    public Rent getById(UUID uuid) {
        return rentRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Аренда не найдена"));
    }

    public void delete(UUID uuid) {
        rentRepository.deleteById(uuid);
    }

    public void save(Rent item, UUID userId) {
        item.setUserId(userId);
        rentRepository.save(item);
    }

    public Rent update(UUID rentId, Rent item) {
        Rent rent = getById(rentId);
        item.setId(rent.getId());
        rentMapper.updateRent(item, rent);
        return rentRepository.save(rent);
    }

    public Rent changeStatusOnCancel(UUID uuid, UUID userId) {
        Rent rent = getById(uuid);
        if (!rentRepository.existsByIdAndUserId(rent.getId(), userId)) {
            throw new AccessDeniedException("Пользователь не имеет доступа к данному ресурсу");
        }
        if (Access.STATUS_CANCEL.getValue().contains(rent.getStatus().getName())) {
            rent.setStatus(statusService.getByName("CANCELED"));
        } else {
            throw new BadRequestException("Невозможно изменить текущий статус в состояние отмены");
        }
        return rentRepository.save(rent);
    }

    public Rent changeStatusOnReturn(UUID uuid, UUID userId) {
        Rent rent = getById(uuid);
        if (!rentRepository.existsByIdAndUserId(rent.getId(), userId)) {
            throw new AccessDeniedException("Пользователь не имеет доступа к данному ресурсу!");
        }
        if (Access.STATUS_RETURN.getValue().contains(rent.getStatus().getName())) {
            rent.setStatus(statusService.getByName("AWAITING_RETURN"));
        } else {
            throw new BadRequestException("Невозможно изменить текущий статус в состояние возврата");
        }
        return rentRepository.save(rent);
    }
}
