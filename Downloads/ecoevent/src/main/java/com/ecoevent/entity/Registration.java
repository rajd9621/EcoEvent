package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status = RegistrationStatus.CONFIRMED;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt = LocalDateTime.now();

    @Column(name = "number_of_tickets")
    private Integer numberOfTickets = 1;

    public enum RegistrationStatus {
        PENDING, CONFIRMED, CANCELLED, ATTENDED, ABSENT
    }
}
