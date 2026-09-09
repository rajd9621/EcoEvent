package com.ecoevent.repository;

import com.ecoevent.entity.WebsitePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WebsitePageRepository extends JpaRepository<WebsitePage, Long> {
    Optional<WebsitePage> findBySlug(String slug);
    List<WebsitePage> findByVisibleTrueOrderByDisplayOrderAsc();
    List<WebsitePage> findAllByOrderByDisplayOrderAsc();
}
