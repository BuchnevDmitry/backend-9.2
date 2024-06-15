package com.edu.rent.service.impl;

import com.edu.rent.access.Access;
import com.edu.rent.api.mapper.RentMapper;
import com.edu.rent.api.mapper.ToolMapper;
import com.edu.rent.api.model.request.ToolQuantityUpdateRequest;
import com.edu.rent.client.ToolClient;
import com.edu.rent.exception.AccessDeniedException;
import com.edu.rent.exception.BadRequestException;
import com.edu.rent.exception.NotFoundException;
import com.edu.rent.model.Rent;
import com.edu.rent.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentService{

    private final RentRepository rentRepository;
    private final StatusService statusService;
    private final ToolClient toolClient;
    private final RentMapper rentMapper;
    private final ToolMapper toolMapper;

    public List<Rent> getAllByUser(UUID uuid, PageRequest pageRequest) {
        return rentRepository.findAllByUserId(uuid, pageRequest).getContent();
    }

    public List<Rent> getAllItems(PageRequest pageRequest) {
        return rentRepository.findAll(pageRequest).getContent();
    }

    public Rent getById(UUID uuid) {
        return rentRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Аренда не найдена"));
    }

    @Transactional
    public void delete(UUID uuid) {
        rentRepository.deleteById(uuid);
    }

    @Transactional
    public void save(Rent item, UUID userId) {
        item.setUserId(userId);
        toolClient.updateQuantity(toolMapper.mapRentToToolQuantity(item.getTools()), "SUBTRACT");
        rentRepository.save(item);
    }

    @Transactional
    public Rent update(UUID rentId, Rent item) {
        Rent rent = getById(rentId);
        item.setId(rent.getId());
        rentMapper.updateRent(item, rent);
        return rentRepository.save(rent);
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public Rent changeStatusOnExtend(UUID uuid, UUID userId) {
        Rent rent = getById(uuid);
        if (!rentRepository.existsByIdAndUserId(rent.getId(), userId)) {
            throw new AccessDeniedException("Пользователь не имеет доступа к данному ресурсу!");
        }
        if (Access.STATUS_RETURN.getValue().contains(rent.getStatus().getName())) {
            rent.setStatus(statusService.getByName("EXTENDED"));
        } else {
            throw new BadRequestException("Невозможно изменить текущий статус в состояние продления");
        }
        return rentRepository.save(rent);
    }
}
