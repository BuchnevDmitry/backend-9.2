package com.edu.rent.service.impl;

import com.edu.rent.model.TimeReceiving;
import com.edu.rent.repository.TimeReceivingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeReceivingService {
    private final TimeReceivingRepository timeReceivingRepository;

    public List<TimeReceiving> getAllItems() {
        return timeReceivingRepository.findAll();
    }

    public TimeReceiving getById(Long id) {
        return timeReceivingRepository.findById(id).orElseThrow(() -> new RuntimeException("Временной промежуток получения не найден"));
    }
}
