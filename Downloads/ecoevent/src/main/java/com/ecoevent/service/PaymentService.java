package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Registration registration, User user, BigDecimal amount) {
        Payment payment = Payment.builder()
                .transactionId("TXN-" + System.currentTimeMillis())
                .amount(amount)
                .status(Payment.PaymentStatus.COMPLETED)
                .method(amount.compareTo(BigDecimal.ZERO) <= 0 ? Payment.PaymentMethod.FREE : Payment.PaymentMethod.UPI)
                .registration(registration)
                .user(user)
                .build();
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> findByUser(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public BigDecimal getTotalRevenue() {
        return paymentRepository.getTotalRevenue();
    }

    public long countCompletedPayments() {
        return paymentRepository.countCompletedPayments();
    }
}
