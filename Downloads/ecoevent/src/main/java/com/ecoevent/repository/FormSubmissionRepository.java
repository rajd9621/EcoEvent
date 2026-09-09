package com.ecoevent.repository;

import com.ecoevent.entity.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {
    List<FormSubmission> findByCustomFormIdOrderBySubmittedAtDesc(Long formId);
}
