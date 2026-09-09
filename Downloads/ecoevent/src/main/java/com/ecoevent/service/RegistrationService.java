package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final TicketService ticketService;
    private final NotificationService notificationService;

    public RegistrationService(RegistrationRepository registrationRepository,
                               TicketService ticketService,
                               NotificationService notificationService) {
        this.registrationRepository = registrationRepository;
        this.ticketService = ticketService;
        this.notificationService = notificationService;
    }

    public Registration register(User user, Event event) {
        if (registrationRepository.existsByUserIdAndEventId(user.getId(), event.getId())) {
            throw new RuntimeException("Already registered for this event");
        }
        if (event.getMaxParticipants() != null && event.getMaxParticipants() > 0) {
            long currentCount = registrationRepository.countByEventId(event.getId());
            if (currentCount >= event.getMaxParticipants()) {
                throw new RuntimeException("Event is fully booked");
            }
        }
        Registration reg = Registration.builder()
                .user(user)
                .event(event)
                .status(Registration.RegistrationStatus.CONFIRMED)
                .numberOfTickets(1)
                .build();
        reg = registrationRepository.save(reg);

        Ticket ticket = ticketService.generateTicket(reg);
        reg.setTicket(ticket);

        notificationService.createNotification(user, "Registration Confirmed",
                "You have successfully registered for " + event.getTitle(),
                "REGISTRATION", "/participant/registrations");

        return reg;
    }

    public List<Registration> findByUser(Long userId) {
        return registrationRepository.findByUserIdOrderByRegisteredAtDesc(userId);
    }

    public List<Registration> findByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public List<Registration> findByOrganizer(Long organizerId) {
        return registrationRepository.findByEventOrganizerId(organizerId);
    }

    public void cancelRegistration(Long id) {
        Registration reg = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        reg.setStatus(Registration.RegistrationStatus.CANCELLED);
        registrationRepository.save(reg);
    }

    public long countByEvent(Long eventId) {
        return registrationRepository.countByEventId(eventId);
    }

    public List<Registration> findAll() {
        return registrationRepository.findAll();
    }
}
