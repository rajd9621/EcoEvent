package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "form_fields")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private CustomForm customForm;

    @NotBlank(message = "Field label is required")
    @Column(nullable = false)
    private String label;

    @Column(name = "field_name")
    private String fieldName;

    @Column(nullable = false)
    private String fieldType;

    @Column(name = "field_options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "placeholder_text")
    private String placeholder;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "is_required")
    private boolean required = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "help_text")
    private String helpText;

    @Column(name = "css_class")
    private String cssClass;

    @Column(name = "min_value")
    private String minValue;

    @Column(name = "max_value")
    private String maxValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
