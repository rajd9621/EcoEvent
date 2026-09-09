package com.ecoevent.repository;

import com.ecoevent.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByRegistrationId(Long registrationId);
    List<Attendance> findByRegistration_EventId(Long eventId);
    long countByStatusAndRegistration_EventId(Attendance.AttendanceStatus status, Long eventId);
}
