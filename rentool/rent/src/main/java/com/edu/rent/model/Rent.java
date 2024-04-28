package com.edu.rent.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rent")
@Getter
@Setter
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "start_date")
    private OffsetDateTime startDate;

    @Column(name = "end_date")
    private OffsetDateTime endDate;

    private Long price;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @OneToOne
    private Status status;

    @OneToOne
    private ReceivingMethod receivingMethod;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rent_tool",
               joinColumns = {@JoinColumn(name = "rent_id")},
               inverseJoinColumns = {@JoinColumn(name = "tool_id")})
    private List<Tool> tools= new ArrayList<>();
}
