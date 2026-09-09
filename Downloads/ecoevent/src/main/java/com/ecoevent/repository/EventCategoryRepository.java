package com.ecoevent.repository;

import com.ecoevent.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {
    Optional<EventCategory> findByNameIgnoreCase(String name);
    List<EventCategory> findByActiveTrueOrderByCreatedAtAsc();
}
