package com.ecoevent.controller;

import com.ecoevent.entity.*;
import com.ecoevent.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class DynamicPageController {

    private final WebsiteService websiteService;
    private final EventService eventService;
    private final DashboardService dashboardService;

    public DynamicPageController(WebsiteService websiteService, EventService eventService,
                                 DashboardService dashboardService) {
        this.websiteService = websiteService;
        this.eventService = eventService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/page/{slug}")
    public String renderDynamicPage(@PathVariable String slug, Model model) {
        try {
            WebsitePage page = websiteService.findPageBySlug(slug);
            if (!page.isVisible()) {
                return "error/404";
            }
            model.addAttribute("page", page);
            model.addAttribute("sections", websiteService.findVisibleSectionsByPage(page.getId()));
            model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
            model.addAttribute("settings", websiteService.getSiteSettings());
            model.addAttribute("theme", websiteService.getActiveTheme());
            model.addAttribute("events", eventService.findPublishedEvents());
            model.addAttribute("stats", dashboardService.getSustainabilityStats());
            return "dynamic/page";
        } catch (Exception e) {
            return "error/404";
        }
    }
}
