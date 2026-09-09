package com.ecoevent.repository;

import com.ecoevent.entity.EventResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EventResourceRepository extends JpaRepository<EventResource, Long> {
    Optional<EventResource> findByEventId(Long eventId);
}
