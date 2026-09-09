package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "themes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "theme_name")
    private String themeName = "Default";

    @Column(name = "primary_color")
    private String primaryColor = "#2E7D32";

    @Column(name = "secondary_color")
    private String secondaryColor = "#81C784";

    @Column(name = "accent_color")
    private String accentColor = "#FFB74D";

    @Column(name = "background_color")
    private String backgroundColor = "#FFFFFF";

    @Column(name = "text_color")
    private String textColor = "#333333";

    @Column(name = "font_family")
    private String fontFamily = "Poppins, sans-serif";

    @Column(name = "button_style")
    private String buttonStyle = "rounded";

    @Column(name = "card_style")
    private String cardStyle = "shadow";

    @Column(name = "header_style")
    private String headerStyle = "solid";

    @Column(name = "footer_style")
    private String footerStyle = "dark";

    @Column(name = "dark_mode")
    private boolean darkMode = false;

    @Column(name = "custom_css", columnDefinition = "TEXT")
    private String customCss;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
