package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class DashboardService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final SustainabilityScoreRepository scoreRepository;
    private final EventWasteRepository wasteRepository;
    private final EventResourceRepository resourceRepository;
    private final PaymentRepository paymentRepository;
    private final FeedbackRepository feedbackRepository;

    public DashboardService(EventRepository eventRepository, UserRepository userRepository,
                            RegistrationRepository registrationRepository,
                            SustainabilityScoreRepository scoreRepository,
                            EventWasteRepository wasteRepository,
                            EventResourceRepository resourceRepository,
                            PaymentRepository paymentRepository,
                            FeedbackRepository feedbackRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.scoreRepository = scoreRepository;
        this.wasteRepository = wasteRepository;
        this.resourceRepository = resourceRepository;
        this.paymentRepository = paymentRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalEvents", eventRepository.count());
        stats.put("sustainableEvents", eventRepository.countSustainableEvents());
        stats.put("totalUsers", userRepository.count());
        stats.put("totalOrganizers", userRepository.countByRole("ROLE_ORGANIZER"));
        stats.put("totalParticipants", userRepository.countByRole("ROLE_PARTICIPANT"));
        stats.put("totalRegistrations", registrationRepository.count());
        stats.put("avgSustainabilityScore", scoreRepository.getAverageScore());
        stats.put("totalRevenue", paymentRepository.getTotalRevenue());
        stats.put("completedPayments", paymentRepository.countCompletedPayments());
        stats.put("totalFeedback", feedbackRepository.count());
        return stats;
    }

    public Map<String, Object> getSustainabilityStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalEvents", eventRepository.count());
        stats.put("sustainableEvents", eventRepository.countSustainableEvents());
        stats.put("avgScore", scoreRepository.getAverageScore());
        stats.put("totalWasteRecords", wasteRepository.count());
        stats.put("totalResourceRecords", resourceRepository.count());
        stats.put("topScores", scoreRepository.findAllByOrderByTotalScoreDesc().stream().limit(5).toList());
        return stats;
    }
}
