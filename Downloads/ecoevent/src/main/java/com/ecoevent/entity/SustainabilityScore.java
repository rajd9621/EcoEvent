package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sustainability_scores")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SustainabilityScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event event;

    @Column(name = "resource_efficiency_score")
    private Integer resourceEfficiencyScore;

    @Column(name = "waste_management_score")
    private Integer wasteManagementScore;

    @Column(name = "food_management_score")
    private Integer foodManagementScore;

    @Column(name = "digitalization_score")
    private Integer digitalizationScore;

    @Column(name = "reusable_materials_score")
    private Integer reusableMaterialsScore;

    @Column(name = "sustainable_vendors_score")
    private Integer sustainableVendorsScore;

    @Column(name = "post_event_reporting_score")
    private Integer postEventReportingScore;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "grade")
    private String grade;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
