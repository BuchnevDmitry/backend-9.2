package com.edu.rent.service;

import com.edu.rent.model.Rent;
import com.edu.rent.repository.RentRepository;
import com.edu.rent.service.impl.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentUpdateService {

    private final RentRepository rentRepository;
    private final StatusService statusService;
    @Transactional
    public void performUpdate() {
        OffsetDateTime now = OffsetDateTime.now();
        log.info("now: " + now);
        List<Rent> rents = rentRepository.findAllByEndDateBefore(now);
        rents.forEach(r -> {
            r.setStatus(statusService.getByName("AWAITING_RETURN"));
            rentRepository.save(r);
        });
    }
}
