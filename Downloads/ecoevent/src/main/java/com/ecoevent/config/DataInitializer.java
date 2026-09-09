package com.ecoevent.config;

import com.ecoevent.entity.*;
import com.ecoevent.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventCategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final WebsitePageRepository pageRepository;
    private final WebsiteSectionRepository sectionRepository;
    private final NavigationItemRepository navigationRepository;
    private final SiteSettingsRepository settingsRepository;
    private final ThemeRepository themeRepository;
    private final SustainabilityMetricRepository metricRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           EventCategoryRepository categoryRepository, EventRepository eventRepository,
                           WebsitePageRepository pageRepository, WebsiteSectionRepository sectionRepository,
                           NavigationItemRepository navigationRepository, SiteSettingsRepository settingsRepository,
                           ThemeRepository themeRepository, SustainabilityMetricRepository metricRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
        this.pageRepository = pageRepository;
        this.sectionRepository = sectionRepository;
        this.navigationRepository = navigationRepository;
        this.settingsRepository = settingsRepository;
        this.themeRepository = themeRepository;
        this.metricRepository = metricRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        String demoPassword = passwordEncoder.encode("demo123");

        // === Demo Users ===
        User admin = User.builder().fullName("Admin User").email("admin@ecoevent.com")
                .password(demoPassword).role("ROLE_ADMIN").enabled(true).phone("9999999999").build();
        User organizer = User.builder().fullName("Event Organizer").email("organizer@ecoevent.com")
                .password(demoPassword).role("ROLE_ORGANIZER").enabled(true).phone("8888888888").organization("GreenEvents Pvt Ltd").build();
        User participant = User.builder().fullName("John Participant").email("participant@ecoevent.com")
                .password(demoPassword).role("ROLE_PARTICIPANT").enabled(true).phone("7777777777").build();
        userRepository.saveAll(List.of(admin, organizer, participant));

        // === Event Categories ===
        EventCategory confCat = EventCategory.builder().name("Conference").description("Professional conferences and summits").iconClass("bi-building").colorCode("#2E7D32").active(true).build();
        EventCategory workshopCat = EventCategory.builder().name("Workshop").description("Hands-on workshops and training").iconClass("bi-tools").colorCode("#1565C0").active(true).build();
        EventCategory festivalCat = EventCategory.builder().name("Festival").description("Community festivals and celebrations").iconClass("bi-music-note-beamed").colorCode("#E65100").active(true).build();
        EventCategory webinarCat = EventCategory.builder().name("Webinar").description("Online webinars and virtual events").iconClass("bi-laptop").colorCode("#6A1B9A").active(true).build();
        categoryRepository.saveAll(List.of(confCat, workshopCat, festivalCat, webinarCat));

        // === Demo Events ===
        Event event1 = Event.builder()
                .title("Green Tech Summit 2025")
                .description("A sustainable technology conference focused on green innovations, renewable energy, and responsible consumption. Features expert speakers, interactive sessions, and showcases of eco-friendly technologies.")
                .location("Bengaluru Convention Centre, Bengaluru")
                .startDate(LocalDateTime.of(2025, 12, 15, 9, 0))
                .endDate(LocalDateTime.of(2025, 12, 16, 18, 0))
                .expectedAttendance(500).actualAttendance(0)
                .ticketPrice(new BigDecimal("500.00")).maxParticipants(600)
                .imageUrl("https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800")
                .published(true).sustainable(true).organizer(organizer).category(confCat)
                .digitalInvitations(true).digitalTickets(true).reusableDecorations(true)
                .wasteSegregation(true).sustainableFoodPractices(true)
                .build();

        Event event2 = Event.builder()
                .title("Zero Waste Workshop")
                .description("Learn practical techniques for reducing waste in daily life and at events. Hands-on sessions on composting, upcycling, and sustainable living practices.")
                .location("Community Hall, Pune")
                .startDate(LocalDateTime.of(2025, 11, 20, 10, 0))
                .endDate(LocalDateTime.of(2025, 11, 20, 16, 0))
                .expectedAttendance(100).actualAttendance(0)
                .ticketPrice(new BigDecimal("0.00")).maxParticipants(120)
                .imageUrl("https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=800")
                .published(true).sustainable(true).organizer(organizer).category(workshopCat)
                .digitalInvitations(true).digitalTickets(true).reusableDecorations(true)
                .wasteSegregation(true).sustainableFoodPractices(true)
                .build();

        Event event3 = Event.builder()
                .title("Eco Festival Bengaluru")
                .description("A community festival celebrating sustainability with eco-friendly stalls, organic food, live music, and workshops on responsible consumption and production.")
                .location("Cubbon Park, Bengaluru")
                .startDate(LocalDateTime.of(2025, 10, 5, 11, 0))
                .endDate(LocalDateTime.of(2025, 10, 5, 20, 0))
                .expectedAttendance(1000).actualAttendance(0)
                .ticketPrice(new BigDecimal("100.00")).maxParticipants(1500)
                .imageUrl("https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=800")
                .published(true).sustainable(true).organizer(organizer).category(festivalCat)
                .digitalInvitations(true).digitalTickets(true).reusableDecorations(true)
                .wasteSegregation(true).sustainableFoodPractices(true)
                .build();
        eventRepository.saveAll(List.of(event1, event2, event3));

        // === Event Resources ===
        // (Resources are created by organizers through the UI; skip here)

        // === Sustainability Metrics ===
        List<SustainabilityMetric> metrics = List.of(
            SustainabilityMetric.builder().categoryName("Resource Efficiency").description("Efficient use of energy, water, and materials").weight(20).iconClass("bi-lightning-charge").active(true).displayOrder(1).build(),
            SustainabilityMetric.builder().categoryName("Waste Management").description("Waste reduction, segregation, and recycling").weight(20).iconClass("bi-recycle").active(true).displayOrder(2).build(),
            SustainabilityMetric.builder().categoryName("Food Management").description("Sustainable food practices and waste reduction").weight(15).iconClass("bi-cup-straw").active(true).displayOrder(3).build(),
            SustainabilityMetric.builder().categoryName("Digitalization").description("Digital invitations, tickets, and materials").weight(15).iconClass("bi-laptop").active(true).displayOrder(4).build(),
            SustainabilityMetric.builder().categoryName("Reusable Materials").description("Use of reusable decorations and materials").weight(10).iconClass("bi-arrow-repeat").active(true).displayOrder(5).build(),
            SustainabilityMetric.builder().categoryName("Sustainable Vendors").description("Local and sustainability-certified vendors").weight(10).iconClass("bi-shop").active(true).displayOrder(6).build(),
            SustainabilityMetric.builder().categoryName("Post-event Reporting").description("Comprehensive post-event sustainability reports").weight(10).iconClass("bi-file-earmark-bar-graph").active(true).displayOrder(7).build()
        );
        metricRepository.saveAll(metrics);

        // === Site Settings ===
        SiteSettings settings = SiteSettings.builder()
                .siteName("EcoEvent")
                .tagline("Smart Sustainable Event Management System")
                .announcementText("Join us in making events more sustainable! Check out our upcoming eco-friendly events.")
                .announcementActive(true)
                .contactEmail("contact@ecoevent.com")
                .contactPhone("+91 80 1234 5678")
                .contactAddress("EcoEvent HQ, MG Road, Bengaluru, Karnataka 560001")
                .footerText("EcoEvent - Building a sustainable future, one event at a time. Aligned with UN SDG 12.")
                .socialFacebook("https://facebook.com/ecoevent")
                .socialTwitter("https://twitter.com/ecoevent")
                .socialInstagram("https://instagram.com/ecoevent")
                .socialLinkedin("https://linkedin.com/company/ecoevent")
                .registrationOpen(true)
                .build();
        settingsRepository.save(settings);

        // === Theme ===
        Theme theme = Theme.builder()
                .themeName("Eco Green")
                .primaryColor("#2E7D32")
                .secondaryColor("#81C784")
                .accentColor("#FFB74D")
                .backgroundColor("#FFFFFF")
                .textColor("#333333")
                .fontFamily("Poppins, sans-serif")
                .buttonStyle("rounded")
                .cardStyle("shadow")
                .headerStyle("solid")
                .footerStyle("dark")
                .darkMode(false)
                .active(true)
                .build();
        themeRepository.save(theme);

        // === Navigation Items ===
        List<NavigationItem> navItems = List.of(
            NavigationItem.builder().label("Home").url("/").displayOrder(1).visible(true).iconClass("bi-house").build(),
            NavigationItem.builder().label("About").url("/about").displayOrder(2).visible(true).iconClass("bi-info-circle").build(),
            NavigationItem.builder().label("Events").url("/events").displayOrder(3).visible(true).iconClass("bi-calendar-event").build(),
            NavigationItem.builder().label("Gallery").url("/gallery").displayOrder(4).visible(true).iconClass("bi-images").build(),
            NavigationItem.builder().label("SDG 12").url("/sdg12").displayOrder(5).visible(true).iconClass("bi-globe").build(),
            NavigationItem.builder().label("Contact").url("/contact").displayOrder(6).visible(true).iconClass("bi-telephone").build()
        );
        navigationRepository.saveAll(navItems);

        // === Website Pages & Sections ===
        WebsitePage homePage = WebsitePage.builder()
                .name("Home").slug("home").title("EcoEvent - Home").description("Homepage")
                .visible(true).systemPage(true).displayOrder(1).build();
        pageRepository.save(homePage);

        List<WebsiteSection> homeSections = List.of(
            WebsiteSection.builder().page(homePage).sectionName("Hero").sectionType("HERO")
                .content("SMART EVENTS. RESPONSIBLE CONSUMPTION. SUSTAINABLE FUTURE.")
                .buttonText("Explore Events").buttonUrl("/events").displayOrder(1).visible(true).enabled(true)
                .cssClass("hero-section").backgroundColor("#1B5E20").build(),
            WebsiteSection.builder().page(homePage).sectionName("Upcoming Events").sectionType("EVENTS")
                .content("Discover and join upcoming sustainable events near you.").displayOrder(2).visible(true).enabled(true)
                .buttonText("View All Events").buttonUrl("/events").build(),
            WebsiteSection.builder().page(homePage).sectionName("SDG 12 Mission").sectionType("SDG_INFORMATION")
                .content("SDG 12 - Responsible Consumption and Production. Our platform integrates sustainability into every event, ensuring resources are optimized, waste is minimized, and responsible practices are promoted throughout the event lifecycle.")
                .displayOrder(3).visible(true).enabled(true).backgroundColor("#F1F8E9").build(),
            WebsiteSection.builder().page(homePage).sectionName("Sustainability Statistics").sectionType("STATISTICS")
                .content("Real-time sustainability metrics across all events on our platform.").displayOrder(4).visible(true).enabled(true).backgroundColor("#FFFFFF").build(),
            WebsiteSection.builder().page(homePage).sectionName("Sustainable Events").sectionType("CARDS")
                .content("Events that meet our high sustainability standards.").displayOrder(5).visible(true).enabled(true).build(),
            WebsiteSection.builder().page(homePage).sectionName("Resource Optimization").sectionType("RESOURCE_STATISTICS")
                .content("Track food, water, electricity, and material usage across events.").displayOrder(6).visible(true).enabled(true).backgroundColor("#F1F8E9").build(),
            WebsiteSection.builder().page(homePage).sectionName("Waste Reduction").sectionType("WASTE_STATISTICS")
                .content("Monitor waste generation, recycling, and reduction efforts.").displayOrder(7).visible(true).enabled(true).backgroundColor("#FFFFFF").build(),
            WebsiteSection.builder().page(homePage).sectionName("Call to Action").sectionType("TEXT")
                .content("Ready to make a difference? Create a sustainable event or join one today!").displayOrder(8).visible(true).enabled(true)
                .buttonText("Create Sustainable Event").buttonUrl("/register").backgroundColor("#2E7D32").build()
        );
        sectionRepository.saveAll(homeSections);

        WebsitePage aboutPage = WebsitePage.builder()
                .name("About").slug("about").title("About EcoEvent").description("About the platform")
                .visible(true).systemPage(true).displayOrder(2).build();
        pageRepository.save(aboutPage);

        sectionRepository.save(WebsiteSection.builder().page(aboutPage).sectionName("About Content").sectionType("TEXT")
                .content("EcoEvent is a Smart Sustainable Event Management System designed to promote UN SDG 12 - Responsible Consumption and Production. Our platform empowers event organizers, participants, and administrators to plan, manage, and participate in events while minimizing environmental impact. From resource planning and waste management to sustainable vendor selection and post-event reporting, EcoEvent integrates sustainability into every step of the event lifecycle.")
                .displayOrder(1).visible(true).enabled(true).build());

        WebsitePage sdgPage = WebsitePage.builder()
                .name("SDG 12").slug("sdg12").title("SDG 12 - Responsible Consumption and Production").description("SDG 12 information")
                .visible(true).systemPage(true).displayOrder(3).build();
        pageRepository.save(sdgPage);

        sectionRepository.save(WebsiteSection.builder().page(sdgPage).sectionName("SDG 12 Info").sectionType("SDG_INFORMATION")
                .content("Sustainable Development Goal 12 aims to ensure sustainable consumption and production patterns. EcoEvent directly contributes to this goal by providing tools for resource optimization, waste management, sustainable vendor selection, and comprehensive sustainability reporting for events. Our platform tracks 17+ sustainability metrics and provides a configurable scoring system out of 100.")
                .displayOrder(1).visible(true).enabled(true).backgroundColor("#F1F8E9").build());

        WebsitePage galleryPage = WebsitePage.builder()
                .name("Gallery").slug("gallery").title("Event Gallery").description("Photos from past events")
                .visible(true).systemPage(true).displayOrder(4).build();
        pageRepository.save(galleryPage);

        WebsitePage contactPage = WebsitePage.builder()
                .name("Contact").slug("contact").title("Contact Us").description("Contact information")
                .visible(true).systemPage(true).displayOrder(5).build();
        pageRepository.save(contactPage);

        sectionRepository.save(WebsiteSection.builder().page(contactPage).sectionName("Contact Info").sectionType("CONTACT")
                .content("Get in touch with us for any queries about sustainable events.").displayOrder(1).visible(true).enabled(true).build());

        System.out.println("=== Demo data initialized successfully ===");
        System.out.println("Admin: admin@ecoevent.com / demo123");
        System.out.println("Organizer: organizer@ecoevent.com / demo123");
        System.out.println("Participant: participant@ecoevent.com / demo123");
    }
}
