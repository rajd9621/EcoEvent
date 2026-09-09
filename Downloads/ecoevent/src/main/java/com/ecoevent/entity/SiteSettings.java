package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "site_settings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SiteSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "site_name")
    private String siteName = "EcoEvent";

    @Column(name = "tagline")
    private String tagline = "Smart Sustainable Event Management System";

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "favicon_url")
    private String faviconUrl;

    @Column(name = "announcement_text")
    private String announcementText;

    @Column(name = "announcement_active")
    private boolean announcementActive = false;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "footer_text")
    private String footerText;

    @Column(name = "social_facebook")
    private String socialFacebook;

    @Column(name = "social_twitter")
    private String socialTwitter;

    @Column(name = "social_instagram")
    private String socialInstagram;

    @Column(name = "social_linkedin")
    private String socialLinkedin;

    @Column(name = "registration_open")
    private boolean registrationOpen = true;

    @Column(name = "maintenance_mode")
    private boolean maintenanceMode = false;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
