package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "website_sections")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class WebsiteSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_id")
    private Long pageId;

    @NotBlank(message = "Section name is required")
    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @Column(name = "section_type", nullable = false)
    private String sectionType;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_url")
    private String image;

    @Column(name = "icon_class")
    private String icon;

    @Column(name = "button_text")
    private String buttonText;

    @Column(name = "button_url")
    private String buttonUrl;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_visible")
    private boolean visible = true;

    @Column(name = "is_enabled")
    private boolean enabled = true;

    @Column(name = "css_class")
    private String cssClass;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", insertable = false, updatable = false)
    private WebsitePage page;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
