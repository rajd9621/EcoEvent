package com.ecoevent.repository;

import com.ecoevent.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRegistrationId(Long registrationId);
    List<Payment> findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = 'COMPLETED'")
    BigDecimal getTotalRevenue();

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'COMPLETED'")
    long countCompletedPayments();
}
