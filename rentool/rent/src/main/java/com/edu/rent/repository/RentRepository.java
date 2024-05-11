package com.edu.rent.repository;

import com.edu.rent.model.Rent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, UUID> {
}
