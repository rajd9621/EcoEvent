package com.ecoevent.repository;

import com.ecoevent.entity.SiteSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SiteSettingsRepository extends JpaRepository<SiteSettings, Long> {
    Optional<SiteSettings> findFirstByOrderByIdAsc();
}
