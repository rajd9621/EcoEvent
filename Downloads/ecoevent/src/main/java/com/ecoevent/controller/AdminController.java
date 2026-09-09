package com.ecoevent.controller;

import com.ecoevent.entity.*;
import com.ecoevent.service.*;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final EventService eventService;
    private final WebsiteService websiteService;
    private final CustomFormService formService;
    private final SustainabilityService sustainabilityService;
    private final FeedbackService feedbackService;
    private final DashboardService dashboardService;
    private final RegistrationService registrationService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    public AdminController(UserService userService, EventService eventService,
                           WebsiteService websiteService, CustomFormService formService,
                           SustainabilityService sustainabilityService, FeedbackService feedbackService,
                           DashboardService dashboardService, RegistrationService registrationService,
                           PaymentService paymentService, NotificationService notificationService) {
        this.userService = userService;
        this.eventService = eventService;
        this.websiteService = websiteService;
        this.formService = formService;
        this.sustainabilityService = sustainabilityService;
        this.feedbackService = feedbackService;
        this.dashboardService = dashboardService;
        this.registrationService = registrationService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    private void addCommon(Model model) {
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getAdminDashboardStats());
        model.addAttribute("recentUsers", userService.findAll().stream().limit(5).toList());
        model.addAttribute("recentEvents", eventService.findAll().stream().limit(5).toList());
        addCommon(model);
        return "admin/dashboard";
    }

    // === Users ===
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("organizers", userService.findByRole("ROLE_ORGANIZER"));
        model.addAttribute("participants", userService.findByRole("ROLE_PARTICIPANT"));
        addCommon(model);
        return "admin/users";
    }

    @GetMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable Long id, RedirectAttributes ra) {
        userService.toggleEnabled(id);
        ra.addFlashAttribute("success", "User status updated");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        userService.deleteUser(id);
        ra.addFlashAttribute("success", "User deleted");
        return "redirect:/admin/users";
    }

    // === Events ===
    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("categories", eventService.findAllCategories());
        addCommon(model);
        return "admin/events";
    }

    @GetMapping("/events/{id}/toggle-publish")
    public String togglePublish(@PathVariable Long id, RedirectAttributes ra) {
        Event e = eventService.findById(id);
        if (e.isPublished()) eventService.unpublishEvent(id);
        else eventService.publishEvent(id);
        ra.addFlashAttribute("success", "Event status updated");
        return "redirect:/admin/events";
    }

    @GetMapping("/events/{id}/delete")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes ra) {
        eventService.deleteEvent(id);
        ra.addFlashAttribute("success", "Event deleted");
        return "redirect:/admin/events";
    }

    // === Categories ===
    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", eventService.findAllCategories());
        addCommon(model);
        return "admin/categories";
    }

    @PostMapping("/categories/save")
    public String saveCategory(EventCategory category, RedirectAttributes ra) {
        eventService.saveCategory(category);
        ra.addFlashAttribute("success", "Category saved");
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        eventService.deleteCategory(id);
        ra.addFlashAttribute("success", "Category deleted");
        return "redirect:/admin/categories";
    }

    // === Registrations ===
    @GetMapping("/registrations")
    public String registrations(Model model) {
        model.addAttribute("registrations", registrationService.findAll());
        addCommon(model);
        return "admin/registrations";
    }

    // === Payments ===
    @GetMapping("/payments")
    public String payments(Model model) {
        model.addAttribute("payments", paymentService.findAll());
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());
        addCommon(model);
        return "admin/payments";
    }

    // === Sustainability ===
    @GetMapping("/sustainability")
    public String sustainability(Model model) {
        model.addAttribute("metrics", sustainabilityService.findAllMetrics());
        model.addAttribute("scores", sustainabilityService.findAllScores());
        model.addAttribute("vendors", sustainabilityService.findAllVendors());
        model.addAttribute("stats", dashboardService.getSustainabilityStats());
        addCommon(model);
        return "admin/sustainability";
    }

    @PostMapping("/sustainability/metrics/save")
    public String saveMetric(SustainabilityMetric metric, RedirectAttributes ra) {
        sustainabilityService.saveMetric(metric);
        ra.addFlashAttribute("success", "Sustainability metric saved");
        return "redirect:/admin/sustainability";
    }

    @GetMapping("/sustainability/metrics/{id}/delete")
    public String deleteMetric(@PathVariable Long id, RedirectAttributes ra) {
        sustainabilityService.deleteMetric(id);
        ra.addFlashAttribute("success", "Metric deleted");
        return "redirect:/admin/sustainability";
    }

    // === Vendors ===
    @PostMapping("/vendors/save")
    public String saveVendor(SustainableVendor vendor, RedirectAttributes ra) {
        sustainabilityService.saveVendor(vendor);
        ra.addFlashAttribute("success", "Vendor saved");
        return "redirect:/admin/sustainability";
    }

    @GetMapping("/vendors/{id}/delete")
    public String deleteVendor(@PathVariable Long id, RedirectAttributes ra) {
        sustainabilityService.deleteVendor(id);
        ra.addFlashAttribute("success", "Vendor deleted");
        return "redirect:/admin/sustainability";
    }

    // === Website Builder - Pages ===
    @GetMapping("/pages")
    public String pages(Model model) {
        model.addAttribute("pages", websiteService.findAllPages());
        addCommon(model);
        return "admin/pages";
    }

    @PostMapping("/pages/save")
    public String savePage(WebsitePage page, RedirectAttributes ra) {
        websiteService.savePage(page);
        ra.addFlashAttribute("success", "Page saved");
        return "redirect:/admin/pages";
    }

    @GetMapping("/pages/{id}/delete")
    public String deletePage(@PathVariable Long id, RedirectAttributes ra) {
        try {
            websiteService.deletePage(id);
            ra.addFlashAttribute("success", "Page deleted");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/pages";
    }

    // === Website Builder - Sections ===
    @GetMapping("/pages/{pageId}/sections")
    public String sections(@PathVariable Long pageId, Model model) {
        WebsitePage page = websiteService.findPageById(pageId);
        model.addAttribute("page", page);
        model.addAttribute("sections", websiteService.findSectionsByPage(pageId));
        model.addAttribute("sectionTypes", List.of("HERO", "TEXT", "IMAGE", "CARDS", "STATISTICS",
                "EVENTS", "EVENT_CATEGORIES", "GALLERY", "TESTIMONIALS", "FAQ", "CONTACT",
                "FORM", "TABLE", "CHART", "SDG_INFORMATION", "SUSTAINABILITY_METRICS",
                "WASTE_STATISTICS", "RESOURCE_STATISTICS", "CUSTOM_CONTENT"));
        addCommon(model);
        return "admin/sections";
    }

    @PostMapping("/sections/save")
    public String saveSection(WebsiteSection section, RedirectAttributes ra) {
        websiteService.saveSection(section);
        ra.addFlashAttribute("success", "Section saved");
        return "redirect:/admin/pages/" + section.getPageId() + "/sections";
    }

    @GetMapping("/sections/{id}/toggle-visibility")
    public String toggleSectionVisibility(@PathVariable Long id, RedirectAttributes ra) {
        WebsiteSection s = websiteService.toggleSectionVisibility(id);
        ra.addFlashAttribute("success", "Section visibility updated");
        return "redirect:/admin/pages/" + s.getPageId() + "/sections";
    }

    @GetMapping("/sections/{id}/toggle-enabled")
    public String toggleSectionEnabled(@PathVariable Long id, RedirectAttributes ra) {
        WebsiteSection s = websiteService.toggleSectionEnabled(id);
        ra.addFlashAttribute("success", "Section enabled/disabled");
        return "redirect:/admin/pages/" + s.getPageId() + "/sections";
    }

    @GetMapping("/sections/{id}/delete")
    public String deleteSection(@PathVariable Long id, RedirectAttributes ra) {
        WebsiteSection s = websiteService.findSectionById(id);
        Long pageId = s.getPageId();
        websiteService.deleteSection(id);
        ra.addFlashAttribute("success", "Section deleted");
        return "redirect:/admin/pages/" + pageId + "/sections";
    }

    // === Navigation Manager ===
    @GetMapping("/navigation")
    public String navigation(Model model) {
        model.addAttribute("navItems", websiteService.findAllNavigationItems());
        addCommon(model);
        return "admin/navigation";
    }

    @PostMapping("/navigation/save")
    public String saveNavItem(NavigationItem item, RedirectAttributes ra) {
        websiteService.saveNavigationItem(item);
        ra.addFlashAttribute("success", "Navigation item saved");
        return "redirect:/admin/navigation";
    }

    @GetMapping("/navigation/{id}/delete")
    public String deleteNavItem(@PathVariable Long id, RedirectAttributes ra) {
        websiteService.deleteNavigationItem(id);
        ra.addFlashAttribute("success", "Navigation item deleted");
        return "redirect:/admin/navigation";
    }

    @GetMapping("/navigation/{id}/toggle")
    public String toggleNavItem(@PathVariable Long id, RedirectAttributes ra) {
        websiteService.toggleNavigationVisibility(id);
        ra.addFlashAttribute("success", "Navigation visibility updated");
        return "redirect:/admin/navigation";
    }

    // === Theme Manager ===
    @GetMapping("/theme")
    public String theme(Model model) {
        model.addAttribute("theme", websiteService.getActiveTheme());
        addCommon(model);
        return "admin/theme";
    }

    @PostMapping("/theme/save")
    public String saveTheme(Theme theme, RedirectAttributes ra) {
        websiteService.saveTheme(theme);
        ra.addFlashAttribute("success", "Theme saved successfully");
        return "redirect:/admin/theme";
    }

    // === Site Settings ===
    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("settings", websiteService.getSiteSettings());
        addCommon(model);
        return "admin/settings";
    }

    @PostMapping("/settings/save")
    public String saveSettings(SiteSettings settings, RedirectAttributes ra) {
        websiteService.saveSiteSettings(settings);
        ra.addFlashAttribute("success", "Site settings saved");
        return "redirect:/admin/settings";
    }

    // === Form Builder ===
    @GetMapping("/forms")
    public String forms(Model model) {
        model.addAttribute("forms", formService.findAllForms());
        addCommon(model);
        return "admin/forms";
    }

    @PostMapping("/forms/save")
    public String saveForm(CustomForm form, RedirectAttributes ra) {
        formService.saveForm(form);
        ra.addFlashAttribute("success", "Form saved");
        return "redirect:/admin/forms";
    }

    @GetMapping("/forms/{id}/delete")
    public String deleteForm(@PathVariable Long id, RedirectAttributes ra) {
        formService.deleteForm(id);
        ra.addFlashAttribute("success", "Form deleted");
        return "redirect:/admin/forms";
    }

    @GetMapping("/forms/{id}/fields")
    public String formFields(@PathVariable Long id, Model model) {
        CustomForm form = formService.findFormById(id);
        model.addAttribute("form", form);
        model.addAttribute("fields", formService.findFieldsByForm(id));
        model.addAttribute("fieldTypes", List.of("TEXT", "EMAIL", "NUMBER", "DATE",
                "DROPDOWN", "RADIO", "CHECKBOX", "TEXTAREA", "FILE_UPLOAD", "RATING"));
        addCommon(model);
        return "admin/form-fields";
    }

    @PostMapping("/forms/{formId}/fields/save")
    public String saveField(@PathVariable Long formId, FormField field, RedirectAttributes ra) {
        field.setCustomForm(formService.findFormById(formId));
        formService.saveField(field);
        ra.addFlashAttribute("success", "Field saved");
        return "redirect:/admin/forms/" + formId + "/fields";
    }

    @GetMapping("/forms/{formId}/fields/{id}/delete")
    public String deleteField(@PathVariable Long formId, @PathVariable Long id, RedirectAttributes ra) {
        formService.deleteField(id);
        ra.addFlashAttribute("success", "Field deleted");
        return "redirect:/admin/forms/" + formId + "/fields";
    }

    @GetMapping("/forms/{id}/submissions")
    public String formSubmissions(@PathVariable Long id, Model model) {
        model.addAttribute("form", formService.findFormById(id));
        model.addAttribute("submissions", formService.findSubmissionsByForm(id));
        addCommon(model);
        return "admin/form-submissions";
    }

    // === Feedback ===
    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute("feedbacks", feedbackService.findAll());
        addCommon(model);
        return "admin/feedback";
    }

    @GetMapping("/feedback/{id}/approve")
    public String approveFeedback(@PathVariable Long id, RedirectAttributes ra) {
        feedbackService.approve(id);
        ra.addFlashAttribute("success", "Feedback approved");
        return "redirect:/admin/feedback";
    }

    @GetMapping("/feedback/{id}/feature")
    public String featureFeedback(@PathVariable Long id, RedirectAttributes ra) {
        feedbackService.toggleFeatured(id);
        ra.addFlashAttribute("success", "Feedback featured status toggled");
        return "redirect:/admin/feedback";
    }

    @GetMapping("/feedback/{id}/delete")
    public String deleteFeedback(@PathVariable Long id, RedirectAttributes ra) {
        feedbackService.delete(id);
        ra.addFlashAttribute("success", "Feedback deleted");
        return "redirect:/admin/feedback";
    }

    // === Notifications ===
    @GetMapping("/notifications")
    public String notifications(Model model) {
        model.addAttribute("users", userService.findAll());
        addCommon(model);
        return "admin/notifications";
    }

    @PostMapping("/notifications/send")
    public String sendNotification(@RequestParam Long userId, @RequestParam String title,
                                   @RequestParam String message, @RequestParam String type,
                                   RedirectAttributes ra) {
        User user = userService.findById(userId);
        notificationService.createNotification(user, title, message, type, null);
        ra.addFlashAttribute("success", "Notification sent");
        return "redirect:/admin/notifications";
    }
}
