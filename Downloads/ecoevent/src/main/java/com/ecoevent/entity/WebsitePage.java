package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "website_pages")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class WebsitePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Page name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "URL slug is required")
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_visible")
    private boolean visible = true;

    @Column(name = "is_system_page")
    private boolean systemPage = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    private List<WebsiteSection> sections = new ArrayList<>();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
