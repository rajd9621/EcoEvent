package com.ecoevent.controller;

import com.ecoevent.dto.UserRegistrationDto;
import com.ecoevent.entity.User;
import com.ecoevent.service.UserService;
import com.ecoevent.service.WebsiteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final WebsiteService websiteService;

    public AuthController(UserService userService, WebsiteService websiteService) {
        this.userService = userService;
        this.websiteService = websiteService;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        if (error != null) model.addAttribute("error", "Invalid email or password");
        if (logout != null) model.addAttribute("message", "You have been logged out successfully");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto dto,
                               BindingResult result, Model model) {
        model.addAttribute("navItems", websiteService.findVisibleNavigationItems());
        model.addAttribute("settings", websiteService.getSiteSettings());
        model.addAttribute("theme", websiteService.getActiveTheme());

        if (result.hasErrors()) {
            return "register";
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.userDto", "Passwords do not match");
            return "register";
        }

        try {
            User user = User.builder()
                    .fullName(dto.getFullName())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .phone(dto.getPhone())
                    .organization(dto.getOrganization())
                    .role(dto.getRole() != null ? dto.getRole() : "ROLE_PARTICIPANT")
                    .enabled(true)
                    .build();
            userService.registerUser(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
