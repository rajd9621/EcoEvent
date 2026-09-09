package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event title is required")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String location;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "expected_attendance")
    private Integer expectedAttendance;

    @Column(name = "actual_attendance")
    private Integer actualAttendance;

    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_published")
    private boolean published = true;

    @Column(name = "is_sustainable")
    private boolean sustainable = true;

    @Column(name = "digital_invitations")
    private boolean digitalInvitations = true;

    @Column(name = "digital_tickets")
    private boolean digitalTickets = true;

    @Column(name = "reusable_decorations")
    private boolean reusableDecorations = false;

    @Column(name = "waste_segregation")
    private boolean wasteSegregation = false;

    @Column(name = "sustainable_food_practices")
    private boolean sustainableFoodPractices = false;

    @Column(name = "post_event_report_generated")
    private boolean postEventReportGenerated = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private EventCategory category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Registration> registrations = new ArrayList<>();

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private EventResource resource;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private EventWaste waste;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private SustainabilityScore sustainabilityScore;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Feedback> feedbacks = new ArrayList<>();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
