package com.edu.tool.repository;

import com.edu.tool.model.Category;
import com.edu.tool.model.Tool;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, UUID> {
    @Query("SELECT t FROM Tool t INNER JOIN t.category c WHERE c IN :categories")
    Page<Tool> findAllByCategories(@Param("categories") List<Category> categories, Pageable pageable);
}
