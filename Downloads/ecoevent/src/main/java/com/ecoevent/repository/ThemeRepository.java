package com.ecoevent.repository;

import com.ecoevent.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByActiveTrue();
}
