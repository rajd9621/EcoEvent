package com.ecoevent.repository;

import com.ecoevent.entity.NavigationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NavigationItemRepository extends JpaRepository<NavigationItem, Long> {
    List<NavigationItem> findByVisibleTrueOrderByDisplayOrderAsc();
    List<NavigationItem> findAllByOrderByDisplayOrderAsc();
    List<NavigationItem> findByParentId(Long parentId);
}
