package com.ecoevent.repository;

import com.ecoevent.entity.CustomForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomFormRepository extends JpaRepository<CustomForm, Long> {
    List<CustomForm> findByActiveTrueOrderByIdDesc();
}
