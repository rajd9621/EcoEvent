package com.ecoevent.repository;

import com.ecoevent.entity.WebsiteSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WebsiteSectionRepository extends JpaRepository<WebsiteSection, Long> {
    List<WebsiteSection> findByPageIdOrderByDisplayOrderAsc(Long pageId);
    List<WebsiteSection> findByPageIdAndVisibleTrueAndEnabledTrueOrderByDisplayOrderAsc(Long pageId);
}
