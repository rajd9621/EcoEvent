package com.ecoevent.controller;

import com.ecoevent.entity.*;
import com.ecoevent.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/participant")
public class ParticipantController {

    private final UserService userService;
    private final EventService eventService;
    private final WebsiteService websiteService;
    private final RegistrationService registrationService;
    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final FeedbackService feedbackService;
    private final NotificationService notificationService;

    public ParticipantController(UserService userService, EventService eventService,
                                  WebsiteService websiteService, RegistrationService registrationService,
                                  TicketService ticketService, PaymentService paymentService,
                                  FeedbackService feedbackService, NotificationService notificationService) {
        this.userService = userService;
        this.eventService = eventService;
        this.websiteService = websiteService;
        this.registrationService = registrationService;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.feedbackService = feedbackService;
        this.notificationService = notificationService;
    }

    private void addCommon(Model model) {
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
    }

    private User getCurrentUser(Authentication auth) {
        return userService.findByEmail(auth.getName());
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("registrations", registrationService.findByUser(user.getId()));
        model.addAttribute("upcomingEvents", eventService.findUpcomingEvents().stream().limit(5).toList());
        model.addAttribute("notifications", notificationService.findByUser(user.getId()));
        model.addAttribute("unreadCount", notificationService.countUnread(user.getId()));
        addCommon(model);
        return "participant/dashboard";
    }

    @GetMapping("/registrations")
    public String myRegistrations(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        model.addAttribute("registrations", registrationService.findByUser(user.getId()));
        addCommon(model);
        return "participant/registrations";
    }

    @GetMapping("/events/{id}/register")
    public String registerForEvent(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        try {
            User user = getCurrentUser(auth);
            Event event = eventService.findById(id);
            Registration reg = registrationService.register(user, event);
            if (event.getTicketPrice() != null && event.getTicketPrice().compareTo(BigDecimal.ZERO) > 0) {
                paymentService.processPayment(reg, user, event.getTicketPrice());
            }
            ra.addFlashAttribute("success", "Successfully registered! Your ticket has been generated.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/participant/registrations";
    }

    @GetMapping("/tickets")
    public String myTickets(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        List<Registration> regs = registrationService.findByUser(user.getId());
        List<Ticket> tickets = new ArrayList<>();
        for (Registration reg : regs) {
            try {
                tickets.add(ticketService.findByRegistration(reg.getId()));
            } catch (Exception ignored) {}
        }
        model.addAttribute("tickets", tickets);
        addCommon(model);
        return "participant/tickets";
    }

    @GetMapping("/feedback")
    public String feedbackForm(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        model.addAttribute("registrations", registrationService.findByUser(user.getId()));
        model.addAttribute("feedbacks", feedbackService.findAll().stream()
                .filter(f -> f.getUser() != null && f.getUser().getId().equals(user.getId())).toList());
        addCommon(model);
        return "participant/feedback";
    }

    @PostMapping("/feedback/submit")
    public String submitFeedback(Authentication auth, @RequestParam Long eventId,
                                  @RequestParam Integer rating, @RequestParam String comment,
                                  @RequestParam(required = false) Integer sustainabilityRating,
                                  RedirectAttributes ra) {
        User user = getCurrentUser(auth);
        Event event = eventService.findById(eventId);
        Feedback feedback = Feedback.builder()
                .event(event).user(user).rating(rating).comment(comment)
                .sustainabilityRating(sustainabilityRating).approved(false).featured(false)
                .build();
        feedbackService.save(feedback);
        ra.addFlashAttribute("success", "Feedback submitted! It will appear after approval.");
        return "redirect:/participant/feedback";
    }

    @GetMapping("/notifications")
    public String notifications(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        model.addAttribute("notifications", notificationService.findByUser(user.getId()));
        notificationService.markAllAsRead(user.getId());
        addCommon(model);
        return "participant/notifications";
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        addCommon(model);
        return "participant/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(Authentication auth, @RequestParam String fullName,
                                 @RequestParam String phone, @RequestParam String organization,
                                 RedirectAttributes ra) {
        User user = getCurrentUser(auth);
        userService.updateProfile(user.getId(), fullName, phone, organization);
        ra.addFlashAttribute("success", "Profile updated");
        return "redirect:/participant/profile";
    }
}
