package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance", uniqueConstraints = @UniqueConstraint(columnNames = {"registration_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status = AttendanceStatus.ABSENT;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "marked_by_id")
    private Long markedById;

    @Column(name = "notes")
    private String notes;

    public enum AttendanceStatus { PRESENT, ABSENT, LATE, EXCUSED }
}
