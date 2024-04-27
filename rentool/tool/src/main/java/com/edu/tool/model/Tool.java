package com.edu.tool.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tool")
@Getter
@Setter
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String model;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price_hour")
    private Long priceHour;

    private Long count;

    @OneToOne
    private Brand brand;

    @OneToOne
    private Category category;

}
