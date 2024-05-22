package com.edu.rent.repository;

import com.edu.rent.model.TimeReceiving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeReceivingRepository extends JpaRepository<TimeReceiving, Long> {
}
