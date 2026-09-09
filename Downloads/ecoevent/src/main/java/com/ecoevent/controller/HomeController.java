package com.ecoevent.controller;

import com.ecoevent.entity.*;
import com.ecoevent.service.*;
import com.ecoevent.repository.SustainableVendorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class HomeController {

    private final EventService eventService;
    private final WebsiteService websiteService;
    private final DashboardService dashboardService;
    private final FeedbackService feedbackService;
    private final SustainableVendorRepository vendorRepository;

    public HomeController(EventService eventService, WebsiteService websiteService,
                          DashboardService dashboardService, FeedbackService feedbackService,
                          SustainableVendorRepository vendorRepository) {
        this.eventService = eventService;
        this.websiteService = websiteService;
        this.dashboardService = dashboardService;
        this.feedbackService = feedbackService;
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("sections", websiteService.findVisibleSectionsByPage(
                websiteService.findPageBySlug("home").getId()));
        model.addAttribute("upcomingEvents", eventService.findUpcomingEvents());
        model.addAttribute("sustainableEvents", eventService.findSustainableEvents());
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        model.addAttribute("stats", dashboardService.getSustainabilityStats());
        model.addAttribute("featuredFeedback", feedbackService.findFeaturedFeedback());
        model.addAttribute("vendors", vendorRepository.findByActiveTrueOrderByIdDesc());
        return "home";
    }

    @GetMapping("/home")
    public String homeRedirect() {
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about(Model model) {
        try {
            WebsitePage page = websiteService.findPageBySlug("about");
            model.addAttribute("page", page);
            model.addAttribute("sections", websiteService.findVisibleSectionsByPage(page.getId()));
        } catch (Exception e) {
            model.addAttribute("sections", Collections.emptyList());
        }
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "about";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("events", eventService.findPublishedEvents());
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "gallery";
    }

    @GetMapping("/sdg12")
    public String sdg12(Model model) {
        try {
            WebsitePage page = websiteService.findPageBySlug("sdg12");
            model.addAttribute("page", page);
            model.addAttribute("sections", websiteService.findVisibleSectionsByPage(page.getId()));
        } catch (Exception e) {
            model.addAttribute("sections", Collections.emptyList());
        }
        model.addAttribute("metrics", dashboardService.getSustainabilityStats());
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "sdg12";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        try {
            WebsitePage page = websiteService.findPageBySlug("contact");
            model.addAttribute("sections", websiteService.findVisibleSectionsByPage(page.getId()));
        } catch (Exception e) {
            model.addAttribute("sections", Collections.emptyList());
        }
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam String name, @RequestParam String email,
                                @RequestParam String subject, @RequestParam String message,
                                Model model) {
        model.addAttribute("success", "Thank you, " + name + "! Your message has been received.");
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "contact";
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(org.springframework.security.core.Authentication auth) {
        if (auth == null) return "redirect:/login";
        String role = auth.getAuthorities().iterator().next().getAuthority();
        return switch (role) {
            case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
            case "ROLE_ORGANIZER" -> "redirect:/organizer/dashboard";
            default -> "redirect:/participant/dashboard";
        };
    }
}
