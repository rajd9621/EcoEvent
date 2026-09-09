package com.ecoevent.repository;
import com.ecoevent.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByRegistrationId(Long registrationId);
    @Query("SELECT a FROM Attendance a WHERE a.registration.event.id = :eventId")
    List<Attendance> findByEventId(@Param("eventId") Long eventId);
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.status = :status AND a.registration.event.id = :eventId")
    long countByStatusAndEventId(@Param("status") Attendance.AttendanceStatus status, @Param("eventId") Long eventId);
}
