package com.edu.rent.repository;

import com.edu.rent.model.ReceivingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivingMethodRepository extends JpaRepository<ReceivingMethod, Long> {
}
