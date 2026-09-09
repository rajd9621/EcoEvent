package com.ecoevent.repository;

import com.ecoevent.entity.SustainabilityMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SustainabilityMetricRepository extends JpaRepository<SustainabilityMetric, Long> {
    List<SustainabilityMetric> findByActiveTrueOrderByDisplayOrderAsc();
    List<SustainabilityMetric> findAllByOrderByDisplayOrderAsc();
}
