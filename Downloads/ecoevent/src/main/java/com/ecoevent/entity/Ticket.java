package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_code", nullable = false, unique = true)
    private String ticketCode;

    @Column(name = "qr_code_path")
    private String qrCodePath;

    @Column(name = "qr_code_base64", columnDefinition = "TEXT")
    private String qrCodeBase64;

    @Column(name = "is_used")
    private boolean used = false;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @PrePersist
    public void generateCode() {
        if (this.ticketCode == null || this.ticketCode.isBlank()) {
            this.ticketCode = "ECO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
