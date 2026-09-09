package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "navigation_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class NavigationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Label is required")
    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String url;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_visible")
    private boolean visible = true;

    @Column(name = "icon_class")
    private String iconClass;

    @Column(name = "open_in_new_tab")
    private boolean openInNewTab = false;

    @Column(name = "is_dropdown")
    private boolean dropdown = false;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
