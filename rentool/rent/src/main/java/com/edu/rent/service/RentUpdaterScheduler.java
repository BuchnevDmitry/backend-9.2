package com.edu.rent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RentUpdaterScheduler {
    private final RentUpdateService rentUpdateService;
    @Scheduled(fixedDelayString = "${app.scheduler-interval}")
    public void update() {
        rentUpdateService.performUpdate();
    }
}
