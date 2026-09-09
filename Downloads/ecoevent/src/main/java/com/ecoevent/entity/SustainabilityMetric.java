package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sustainability_metrics")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SustainabilityMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Column(nullable = false)
    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Weight is required")
    @Column(nullable = false)
    private Integer weight;

    @Column(name = "icon_class")
    private String iconClass;

    @Column(name = "max_score")
    private Integer maxScore = 100;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
