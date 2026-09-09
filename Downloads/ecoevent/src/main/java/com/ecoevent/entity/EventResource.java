package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_resources")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EventResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event event;

    @Column(name = "food_requirement")
    private BigDecimal foodRequirement;

    @Column(name = "food_consumed")
    private BigDecimal foodConsumed;

    @Column(name = "food_remaining")
    private BigDecimal foodRemaining;

    @Column(name = "water_requirement")
    private BigDecimal waterRequirement;

    @Column(name = "water_consumed")
    private BigDecimal waterConsumed;

    @Column(name = "electricity_usage")
    private BigDecimal electricityUsage;

    @Column(name = "printed_materials")
    private Integer printedMaterials;

    @Column(name = "reusable_materials")
    private Integer reusableMaterials;

    @Column(name = "plastic_usage")
    private BigDecimal plasticUsage;

    @Column(name = "paper_usage")
    private BigDecimal paperUsage;

    @Column(name = "local_vendors_count")
    private Integer localVendorsCount;

    @Column(name = "digital_invitations_sent")
    private Integer digitalInvitationsSent;

    @Column(name = "digital_tickets_issued")
    private Integer digitalTicketsIssued;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
