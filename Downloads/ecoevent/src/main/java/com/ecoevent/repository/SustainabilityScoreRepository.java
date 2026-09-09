package com.ecoevent.repository;

import com.ecoevent.entity.SustainabilityScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SustainabilityScoreRepository extends JpaRepository<SustainabilityScore, Long> {
    Optional<SustainabilityScore> findByEventId(Long eventId);
    List<SustainabilityScore> findAllByOrderByTotalScoreDesc();

    @Query("SELECT COALESCE(AVG(s.totalScore), 0) FROM SustainabilityScore s")
    Double getAverageScore();
}
