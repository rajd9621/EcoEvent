package com.ecoevent.repository;

import com.ecoevent.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketCode(String ticketCode);
    Optional<Ticket> findByRegistrationId(Long registrationId);
    long countByUsedTrue();
}
