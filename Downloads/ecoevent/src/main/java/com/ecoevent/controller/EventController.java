package com.ecoevent.controller;

import com.ecoevent.entity.*;
import com.ecoevent.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final WebsiteService websiteService;
    private final FeedbackService feedbackService;
    private final SustainabilityService sustainabilityService;

    public EventController(EventService eventService, WebsiteService websiteService,
                           FeedbackService feedbackService, SustainabilityService sustainabilityService) {
        this.eventService = eventService;
        this.websiteService = websiteService;
        this.feedbackService = feedbackService;
        this.sustainabilityService = sustainabilityService;
    }

    @GetMapping
    public String listEvents(@RequestParam(required = false) String search,
                             @RequestParam(required = false) Long categoryId,
                             Model model) {
        List<Event> events;
        if (search != null && !search.isBlank()) {
            events = eventService.search(search);
        } else if (categoryId != null) {
            events = eventService.findByCategory(categoryId);
        } else {
            events = eventService.findPublishedEvents();
        }
        model.addAttribute("events", events);
        model.addAttribute("categories", eventService.findAllCategories());
        model.addAttribute("search", search);
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "events";
    }

    @GetMapping("/{id}")
    public String eventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("feedbacks", feedbackService.findByEvent(id));
        model.addAttribute("score", sustainabilityService.findScoreByEvent(id).orElse(null));
        model.addAttribute("resource", sustainabilityService.findResourceByEvent(id).orElse(null));
        model.addAttribute("waste", sustainabilityService.findWasteByEvent(id).orElse(null));
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "event-details";
    }
}
