package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sustainable_vendors")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SustainableVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vendor name is required")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "vendor_type")
    private String vendorType;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "is_local")
    private boolean local = true;

    @Column(name = "sustainability_certified")
    private boolean sustainabilityCertified = false;

    @Column(name = "certification_details")
    private String certificationDetails;

    @Column(name = "eco_practices", columnDefinition = "TEXT")
    private String ecoPractices;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
