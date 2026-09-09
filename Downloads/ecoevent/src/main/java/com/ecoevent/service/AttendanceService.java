package com.ecoevent.service;

import com.ecoevent.entity.Attendance;
import com.ecoevent.entity.Registration;
import com.ecoevent.repository.AttendanceRepository;
import com.ecoevent.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final RegistrationRepository registrationRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                              RegistrationRepository registrationRepository) {
        this.attendanceRepository = attendanceRepository;
        this.registrationRepository = registrationRepository;
    }

    public Attendance markAttendance(Long registrationId, Attendance.AttendanceStatus status, Long markedBy) {
        Registration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        Attendance attendance = attendanceRepository.findByRegistrationId(registrationId)
                .orElse(Attendance.builder().registration(reg).build());
        attendance.setStatus(status);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setMarkedById(markedBy);
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> findByEvent(Long eventId) {
        return attendanceRepository.findByRegistration_EventId(eventId);
    }

    public Optional<Attendance> findByRegistration(Long registrationId) {
        return attendanceRepository.findByRegistrationId(registrationId);
    }

    public long countPresent(Long eventId) {
        return attendanceRepository.countByStatusAndRegistration_EventId(Attendance.AttendanceStatus.PRESENT, eventId);
    }

    public long countAbsent(Long eventId) {
        return attendanceRepository.countByStatusAndRegistration_EventId(Attendance.AttendanceStatus.ABSENT, eventId);
    }
}
