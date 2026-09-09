package com.ecoevent.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method = PaymentMethod.ONLINE;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public enum PaymentStatus { PENDING, COMPLETED, FAILED, REFUNDED }
    public enum PaymentMethod { ONLINE, UPI, CARD, BANK_TRANSFER, CASH, FREE }
}
