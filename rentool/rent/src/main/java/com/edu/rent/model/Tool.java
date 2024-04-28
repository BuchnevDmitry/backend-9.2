package com.edu.rent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Tool {
    @Id
    private UUID id;
}
