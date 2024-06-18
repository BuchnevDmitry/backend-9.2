package com.edu.rent.service.impl;

import com.edu.rent.access.Access;
import com.edu.rent.api.mapper.RentMapper;
import com.edu.rent.api.mapper.ToolMapper;
import com.edu.rent.api.model.request.RentCostRequest;
import com.edu.rent.api.model.request.RentExtendRequest;
import com.edu.rent.api.model.request.ToolQuantityUpdateRequest;
import com.edu.rent.api.model.response.ListToolResponse;
import com.edu.rent.api.model.response.ToolResponse;
import com.edu.rent.client.ToolClient;
import com.edu.rent.exception.AccessDeniedException;
import com.edu.rent.exception.BadRequestException;
import com.edu.rent.exception.NotFoundException;
import com.edu.rent.model.Rent;
import com.edu.rent.model.RentTool;
import com.edu.rent.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Rent save(Rent item, UUID userId) {
        item.setUserId(userId);
        toolClient.updateQuantity(toolMapper.mapRentToToolQuantity(item.getTools()), "SUBTRACT");
        RentCostRequest rentCostRequest = new RentCostRequest(item.getStartDate(), item.getEndDate(), item.getTools().stream().toList());
        item.setPrice(calculationCost(rentCostRequest));
        return rentRepository.save(item);
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
    public Rent changeStatusOnExtend(UUID rentId, RentExtendRequest request, UUID userId) {
        Rent rent = getById(rentId);
        if (!rentRepository.existsByIdAndUserId(rent.getId(), userId)) {
            throw new AccessDeniedException("Пользователь не имеет доступа к данному ресурсу!");
        }
        if (Access.STATUS_RETURN.getValue().contains(rent.getStatus().getName())) {
            rent.setEndDate(request.endDate());
            rent.setStatus(statusService.getByName("EXTENDED"));
        } else {
            throw new BadRequestException("Невозможно изменить текущий статус в состояние продления");
        }
        return rentRepository.save(rent);
    }

    @Transactional
    public Rent changeStatusOnComplete(UUID id) {
        Rent rent = getById(id);
        if (rent.getStatus().getName().equals("COMPLETED")) {
            throw new BadRequestException("Данная аренда уже завершена");
        }
        toolClient.updateQuantity(toolMapper.mapRentToToolQuantity(rent.getTools()), "ADD");
        rent.setStatus(statusService.getByName("COMPLETED"));
        return rentRepository.save(rent);
    }

    public Long calculationCost(RentCostRequest request) {
        List<RentTool> rentTools = request.tools();
        List<UUID> ids = rentTools.stream().map(RentTool::getToolId).toList();
        ListToolResponse toolResponse = toolClient.fetchTools(ids);
        if (toolResponse.size() != ids.size()){
            throw new BadRequestException("Некорректный запрос. Инструмента с таким id не существует");
        }
        Map<UUID, Long> toolMap = rentTools.stream()
            .collect(Collectors.toMap(RentTool::getToolId, RentTool::getCountTool));
        long cost = 0L;
        Duration duration = Duration.between(request.startDate(), request.endDate());
        long days = duration.toDays();
        for (ToolResponse tool: toolResponse.tools()) {
            long costTool = toolMap.get(tool.id()) * tool.priceDay() * days;
            cost += costTool;
        }
        return cost;
    }
}
