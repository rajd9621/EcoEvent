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
@RequestMapping("/organizer")
public class OrganizerController {

    private final EventService eventService;
    private final WebsiteService websiteService;
    private final RegistrationService registrationService;
    private final SustainabilityService sustainabilityService;
    private final AttendanceService attendanceService;
    private final FeedbackService feedbackService;
    private final UserService userService;

    public OrganizerController(EventService eventService, WebsiteService websiteService,
                               RegistrationService registrationService,
                               SustainabilityService sustainabilityService,
                               AttendanceService attendanceService,
                               FeedbackService feedbackService, UserService userService) {
        this.eventService = eventService;
        this.websiteService = websiteService;
        this.registrationService = registrationService;
        this.sustainabilityService = sustainabilityService;
        this.attendanceService = attendanceService;
        this.feedbackService = feedbackService;
        this.userService = userService;
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
        List<Event> events = eventService.findByOrganizer(user.getId());
        model.addAttribute("events", events);
        model.addAttribute("totalEvents", events.size());
        model.addAttribute("organizer", user);
        addCommon(model);
        return "organizer/dashboard";
    }

    @GetMapping("/events/create")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", eventService.findAllCategories());
        addCommon(model);
        return "organizer/event-form";
    }

    @PostMapping("/events/save")
    public String saveEvent(Authentication auth, Event event, RedirectAttributes ra) {
        User user = getCurrentUser(auth);
        event.setOrganizer(user);
        eventService.save(event);
        ra.addFlashAttribute("success", "Event saved successfully");
        return "redirect:/organizer/events/" + event.getId() + "/edit";
    }

    @GetMapping("/events/{id}/edit")
    public String editEvent(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("categories", eventService.findAllCategories());
        model.addAttribute("registrations", registrationService.findByEvent(id));
        model.addAttribute("score", sustainabilityService.findScoreByEvent(id).orElse(null));
        model.addAttribute("resource", sustainabilityService.findResourceByEvent(id).orElse(new EventResource()));
        model.addAttribute("waste", sustainabilityService.findWasteByEvent(id).orElse(new EventWaste()));
        model.addAttribute("feedbacks", feedbackService.findByEvent(id));
        addCommon(model);
        return "organizer/event-edit";
    }

    @GetMapping("/events/{id}/delete")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes ra) {
        eventService.deleteEvent(id);
        ra.addFlashAttribute("success", "Event deleted");
        return "redirect:/organizer/dashboard";
    }

    // === Resource Management ===
    @PostMapping("/events/{id}/resource/save")
    public String saveResource(@PathVariable Long id, EventResource resource, RedirectAttributes ra) {
        Event event = eventService.findById(id);
        resource.setEvent(event);
        sustainabilityService.saveResource(resource);
        ra.addFlashAttribute("success", "Resource data saved");
        return "redirect:/organizer/events/" + id + "/edit";
    }

    // === Waste Management ===
    @PostMapping("/events/{id}/waste/save")
    public String saveWaste(@PathVariable Long id, EventWaste waste, RedirectAttributes ra) {
        Event event = eventService.findById(id);
        waste.setEvent(event);
        sustainabilityService.saveWaste(waste);
        ra.addFlashAttribute("success", "Waste data saved");
        return "redirect:/organizer/events/" + id + "/edit";
    }

    // === Calculate Score ===
    @PostMapping("/events/{id}/calculate-score")
    public String calculateScore(@PathVariable Long id, RedirectAttributes ra) {
        Event event = eventService.findById(id);
        event.setPostEventReportGenerated(true);
        eventService.save(event);
        sustainabilityService.calculateScore(event);
        ra.addFlashAttribute("success", "Sustainability score calculated");
        return "redirect:/organizer/events/" + id + "/edit";
    }

    // === Attendance ===
    @GetMapping("/events/{id}/attendance")
    public String attendance(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("registrations", registrationService.findByEvent(id));
        model.addAttribute("attendanceRecords", attendanceService.findByEvent(id));
        model.addAttribute("presentCount", attendanceService.countPresent(id));
        model.addAttribute("absentCount", attendanceService.countAbsent(id));
        addCommon(model);
        return "organizer/attendance";
    }

    @PostMapping("/events/{eventId}/attendance/{regId}/mark")
    public String markAttendance(@PathVariable Long eventId, @PathVariable Long regId,
                                 @RequestParam Attendance.AttendanceStatus status,
                                 Authentication auth, RedirectAttributes ra) {
        User user = getCurrentUser(auth);
        attendanceService.markAttendance(regId, status, user.getId());
        ra.addFlashAttribute("success", "Attendance marked");
        return "redirect:/organizer/events/" + eventId + "/attendance";
    }

    // === Registrations ===
    @GetMapping("/registrations")
    public String registrations(Authentication auth, Model model) {
        User user = getCurrentUser(auth);
        model.addAttribute("registrations", registrationService.findByOrganizer(user.getId()));
        addCommon(model);
        return "organizer/registrations";
    }

    // === Vendors ===
    @GetMapping("/vendors")
    public String vendors(Model model) {
        model.addAttribute("vendors", sustainabilityService.findAllVendors());
        addCommon(model);
        return "organizer/vendors";
    }
}
