package com.ecoevent.repository;

import com.ecoevent.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPublishedTrueOrderByStartDateAsc();
    List<Event> findByOrganizerIdOrderByCreatedAtDesc(Long organizerId);
    List<Event> findByCategoryId(Long categoryId);
    List<Event> findBySustainableTrueAndPublishedTrue();

    @Query("SELECT e FROM Event e WHERE e.published = true AND e.startDate >= :now ORDER BY e.startDate ASC")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.published = true AND e.startDate < :now ORDER BY e.startDate DESC")
    List<Event> findPastEvents(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.sustainable = true AND e.published = true")
    long countSustainableEvents();

    @Query("SELECT e FROM Event e WHERE e.published = true AND (LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.location) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Event> searchEvents(@Param("keyword") String keyword);

    @Query("SELECT e FROM Event e WHERE e.published = true AND e.startDate >= :startDate AND e.startDate <= :endDate ORDER BY e.startDate ASC")
    List<Event> findEventsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
