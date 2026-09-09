package com.ecoevent.repository;

import com.ecoevent.entity.SustainableVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SustainableVendorRepository extends JpaRepository<SustainableVendor, Long> {
    List<SustainableVendor> findByActiveTrueOrderByIdDesc();
    List<SustainableVendor> findByLocalTrue(boolean local);
    List<SustainableVendor> findBySustainabilityCertifiedTrue();
}
