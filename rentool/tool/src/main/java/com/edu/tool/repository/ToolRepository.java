package com.edu.tool.repository;

import com.edu.tool.model.Tool;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, UUID> {
}
