package com.edu.tool.repository;

import com.edu.tool.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandController extends JpaRepository<Brand, Long> {
}
