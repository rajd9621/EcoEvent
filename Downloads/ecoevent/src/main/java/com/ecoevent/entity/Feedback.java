package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "sustainability_rating")
    private Integer sustainabilityRating;

    @Column(name = "is_approved")
    private boolean approved = false;

    @Column(name = "is_featured")
    private boolean featured = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
