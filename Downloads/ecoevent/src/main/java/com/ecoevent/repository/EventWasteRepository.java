package com.ecoevent.repository;

import com.ecoevent.entity.EventWaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventWasteRepository extends JpaRepository<EventWaste, Long> {
    Optional<EventWaste> findByEventId(Long eventId);
    List<EventWaste> findAllByOrderByCreatedAtDesc();
}
