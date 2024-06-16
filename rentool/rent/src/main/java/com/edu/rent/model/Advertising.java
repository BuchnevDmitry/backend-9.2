package com.edu.rent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "advertising")
@Getter
@Setter
public class Advertising {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;
}
