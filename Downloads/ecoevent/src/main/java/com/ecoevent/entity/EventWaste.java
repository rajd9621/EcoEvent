package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_waste")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EventWaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event event;

    @Column(name = "organic_waste")
    private BigDecimal organicWaste;

    @Column(name = "plastic_waste")
    private BigDecimal plasticWaste;

    @Column(name = "paper_waste")
    private BigDecimal paperWaste;

    @Column(name = "e_waste")
    private BigDecimal eWaste;

    @Column(name = "recyclable_waste")
    private BigDecimal recyclableWaste;

    @Column(name = "waste_recycled")
    private BigDecimal wasteRecycled;

    @Column(name = "total_waste_generated")
    private BigDecimal totalWasteGenerated;

    @Column(name = "waste_segregation_done")
    private boolean wasteSegregationDone = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
