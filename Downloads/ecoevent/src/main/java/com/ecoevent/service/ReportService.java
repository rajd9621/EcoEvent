package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.util.PDFReportUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ReportService {

    private final EventService eventService;
    private final SustainabilityService sustainabilityService;
    private final RegistrationService registrationService;
    private final FeedbackService feedbackService;
    private final AttendanceService attendanceService;
    private final PDFReportUtil pdfReportUtil;

    public ReportService(EventService eventService, SustainabilityService sustainabilityService,
                         RegistrationService registrationService, FeedbackService feedbackService,
                         AttendanceService attendanceService, PDFReportUtil pdfReportUtil) {
        this.eventService = eventService;
        this.sustainabilityService = sustainabilityService;
        this.registrationService = registrationService;
        this.feedbackService = feedbackService;
        this.attendanceService = attendanceService;
        this.pdfReportUtil = pdfReportUtil;
    }

    public byte[] generateEventSustainabilityReport(Long eventId) {
        Event event = eventService.findById(eventId);
        EventResource resource = sustainabilityService.findResourceByEvent(eventId).orElse(null);
        EventWaste waste = sustainabilityService.findWasteByEvent(eventId).orElse(null);
        SustainabilityScore score = sustainabilityService.findScoreByEvent(eventId).orElse(null);
        List<Registration> registrations = registrationService.findByEvent(eventId);
        List<Feedback> feedbacks = feedbackService.findByEvent(eventId);
        long presentCount = attendanceService.countPresent(eventId);

        if (score == null) {
            score = sustainabilityService.calculateScore(event);
        }

        return pdfReportUtil.generateEventReport(event, resource, waste, score,
                registrations.size(), (int) presentCount, feedbacks);
    }
}
