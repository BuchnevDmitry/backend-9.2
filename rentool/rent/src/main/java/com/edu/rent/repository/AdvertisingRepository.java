package com.edu.rent.repository;

import com.edu.rent.model.Advertising;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisingRepository extends JpaRepository<Advertising, Long> {
}
