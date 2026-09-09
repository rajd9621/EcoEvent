package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "notification_type")
    private String type;

    @Column(name = "is_read")
    private boolean read = false;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
