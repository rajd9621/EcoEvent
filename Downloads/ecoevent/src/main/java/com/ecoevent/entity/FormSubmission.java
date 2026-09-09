package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "form_submissions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FormSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private CustomForm customForm;

    @Column(name = "submitted_data", columnDefinition = "TEXT")
    private String submittedData;

    @Column(name = "submitter_ip")
    private String submitterIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt = LocalDateTime.now();
}
