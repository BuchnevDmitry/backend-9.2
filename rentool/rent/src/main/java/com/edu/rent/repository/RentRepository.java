package com.edu.rent.repository;

import com.edu.rent.model.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<Rent, UUID> {
    Page<Rent> findAllByUserId(UUID uuid, Pageable pageable);
    boolean existsByIdAndUserId(UUID rentId, UUID userId);

    @Query("SELECT r FROM Rent r WHERE r.endDate < :time and r.status.id <> 4 and r.status.id <> 6")
    List<Rent> findAllByEndDateBefore(OffsetDateTime time);

    //2024-06-16 01:35:40.375000 +00:00
    //2024-06-16T01:36:02.903459+03:00
}
