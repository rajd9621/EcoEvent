package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class WebsiteService {

    private final WebsitePageRepository pageRepository;
    private final WebsiteSectionRepository sectionRepository;
    private final NavigationItemRepository navigationRepository;
    private final SiteSettingsRepository settingsRepository;
    private final ThemeRepository themeRepository;

    public WebsiteService(WebsitePageRepository pageRepository,
                         WebsiteSectionRepository sectionRepository,
                         NavigationItemRepository navigationRepository,
                         SiteSettingsRepository settingsRepository,
                         ThemeRepository themeRepository) {
        this.pageRepository = pageRepository;
        this.sectionRepository = sectionRepository;
        this.navigationRepository = navigationRepository;
        this.settingsRepository = settingsRepository;
        this.themeRepository = themeRepository;
    }

    // --- Pages ---
    public List<WebsitePage> findAllPages() {
        return pageRepository.findAllByOrderByDisplayOrderAsc();
    }

    public WebsitePage findPageBySlug(String slug) {
        return pageRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Page not found: " + slug));
    }

    public WebsitePage findPageById(Long id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Page not found: " + id));
    }

    public WebsitePage savePage(WebsitePage page) {
        return pageRepository.save(page);
    }

    public void deletePage(Long id) {
        WebsitePage page = findPageById(id);
        if (page.isSystemPage()) {
            throw new RuntimeException("System pages cannot be deleted");
        }
        pageRepository.deleteById(id);
    }

    // --- Sections ---
    public List<WebsiteSection> findSectionsByPage(Long pageId) {
        return sectionRepository.findByPageIdOrderByDisplayOrderAsc(pageId);
    }

    public List<WebsiteSection> findVisibleSectionsByPage(Long pageId) {
        return sectionRepository.findByPageIdAndVisibleTrueAndEnabledTrueOrderByDisplayOrderAsc(pageId);
    }

    public WebsiteSection findSectionById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found: " + id));
    }

    public WebsiteSection saveSection(WebsiteSection section) {
        return sectionRepository.save(section);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }

    public WebsiteSection toggleSectionVisibility(Long id) {
        WebsiteSection s = findSectionById(id);
        s.setVisible(!s.isVisible());
        return sectionRepository.save(s);
    }

    public WebsiteSection toggleSectionEnabled(Long id) {
        WebsiteSection s = findSectionById(id);
        s.setEnabled(!s.isEnabled());
        return sectionRepository.save(s);
    }

    public void reorderSections(List<Long> sectionIds) {
        for (int i = 0; i < sectionIds.size(); i++) {
            WebsiteSection s = findSectionById(sectionIds.get(i));
            s.setDisplayOrder(i);
            sectionRepository.save(s);
        }
    }

    // --- Navigation ---
    public List<NavigationItem> findAllNavigationItems() {
        return navigationRepository.findAllByOrderByDisplayOrderAsc();
    }

    public List<NavigationItem> findVisibleNavigationItems() {
        return navigationRepository.findByVisibleTrueOrderByDisplayOrderAsc();
    }

    public NavigationItem saveNavigationItem(NavigationItem item) {
        return navigationRepository.save(item);
    }

    public void deleteNavigationItem(Long id) {
        navigationRepository.deleteById(id);
    }

    public NavigationItem toggleNavigationVisibility(Long id) {
        NavigationItem item = navigationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Navigation item not found"));
        item.setVisible(!item.isVisible());
        return navigationRepository.save(item);
    }

    // --- Site Settings ---
    public SiteSettings getSiteSettings() {
        return settingsRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> settingsRepository.save(SiteSettings.builder()
                        .siteName("EcoEvent")
                        .tagline("Smart Sustainable Event Management System")
                        .build()));
    }

    public SiteSettings saveSiteSettings(SiteSettings settings) {
        return settingsRepository.save(settings);
    }

    // --- Theme ---
    public Theme getActiveTheme() {
        return themeRepository.findByActiveTrue()
                .orElseGet(() -> {
                    Theme theme = Theme.builder().themeName("Default").active(true).build();
                    return themeRepository.save(theme);
                });
    }

    public Theme saveTheme(Theme theme) {
        // Deactivate all other themes
        themeRepository.findAll().forEach(t -> {
            t.setActive(false);
            themeRepository.save(t);
        });
        theme.setActive(true);
        return themeRepository.save(theme);
    }
}
