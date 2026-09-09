package com.ecoevent.repository;

import com.ecoevent.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserIdOrderByRegisteredAtDesc(Long userId);
    List<Registration> findByEventId(Long eventId);
    Optional<Registration> findByUserIdAndEventId(Long userId, Long eventId);
    long countByEventId(Long eventId);

    @Query("SELECT r FROM Registration r WHERE r.event.organizer.id = :organizerId ORDER BY r.registeredAt DESC")
    List<Registration> findByEventOrganizerId(@Param("organizerId") Long organizerId);

    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}
