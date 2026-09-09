# EcoEvent – Smart Sustainable Event Management System

A production-style web application built with **Java 21** and **Spring Boot** that integrates **UN SDG 12 – Responsible Consumption and Production** into every aspect of event management.

## 🌿 Overview

EcoEvent is a CMS-like event management platform where Admins, Event Organizers, and Participants collaborate to plan, manage, and participate in sustainable events. The platform tracks 17+ sustainability metrics, generates a configurable sustainability score out of 100, produces PDF reports, and allows complete website customization through an Admin Panel — without touching source code.

## 🛠️ Technology Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 21, Spring Boot 3.2.5 |
| Web | Spring MVC, Thymeleaf |
| Security | Spring Security (BCrypt, role-based) |
| Data | Spring Data JPA, Hibernate |
| Database | MySQL (production), H2 (development) |
| Build | Maven |
| UI | HTML5, CSS3, Bootstrap 5, Bootstrap Icons |
| QR Codes | Google ZXing |
| PDF Reports | OpenPDF (LibrePDF) |
| Excel | Apache POI |

## 📋 Prerequisites

- Java 21 (or higher)
- Maven 3.8+
- MySQL 8.0+ (for production)

## 🚀 Running the Application

### Quick Start (H2 in-memory database)

```bash
mvn spring-boot:run
```

Open http://localhost:8080 in your browser.

### Production (MySQL)

1. Create a MySQL database:
   ```sql
   CREATE DATABASE ecoevent;
   ```

2. Edit `src/main/resources/application.properties`:
   - Comment out the H2 section
   - Uncomment the MySQL section
   - Set your database credentials

3. Run:
   ```bash
   mvn spring-boot:run
   ```

## 👤 Demo Accounts

The application seeds demo data on first run. **Change these passwords in any real deployment.**

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@ecoevent.com | demo123 |
| Organizer | organizer@ecoevent.com | demo123 |
| Participant | participant@ecoevent.com | demo123 |

## 🏗️ Architecture

```
com.ecoevent/
├── EcoEventApplication.java          # Main entry point
├── config/
│   ├── SecurityConfig.java           # Spring Security configuration
│   └── DataInitializer.java          # Demo data seeding
├── entity/                           # 22 JPA entities
├── repository/                       # 22 Spring Data JPA repositories
├── service/                          # 13 service classes (business logic)
├── controller/                       # 8 controllers (web layer)
├── dto/                              # Data Transfer Objects
├── security/
│   └── CustomUserDetailsService.java
├── exception/
│   └── GlobalExceptionHandler.java
└── util/
    ├── QRCodeUtil.java               # QR code generation
    └── PDFReportUtil.java            # PDF report generation
```

## 📊 Database Entities

**Core:** User, Event, EventCategory, Registration, Ticket, Payment

**Website Builder:** WebsitePage, WebsiteSection, NavigationItem, SiteSettings, Theme

**Forms:** CustomForm, FormField, FormSubmission

**Sustainability:** SustainabilityMetric, EventResource, EventWaste, SustainableVendor, SustainabilityScore

**Other:** Feedback, Attendance, Notification

## 🌍 SDG 12 Integration

### Sustainability Metrics Tracked Per Event

- Food requirement, consumed, remaining
- Water requirement, consumed
- Electricity usage
- Printed materials, reusable materials
- Plastic usage, paper usage
- Organic waste, plastic waste, paper waste, e-waste
- Recyclable waste, waste recycled
- Sustainable vendors, local vendors
- Digital invitations, digital tickets
- Reusable decorations, waste segregation
- Sustainable food practices
- Post-event sustainability report

### Configurable Sustainability Score (out of 100)

| Category | Default Weight |
|----------|---------------|
| Resource Efficiency | 20 |
| Waste Management | 20 |
| Food Management | 15 |
| Digitalization | 15 |
| Reusable Materials | 10 |
| Sustainable Vendors | 10 |
| Post-event Reporting | 10 |

Admins can modify these categories and weights through the Admin Panel without touching Java source code.

## 🎨 Website Builder (CMS-like)

The Admin can fully customize the website:

- **Pages:** Create, edit, delete, hide/show pages
- **Sections:** Add, remove, reorder, hide/show, rename sections
- **Section Types:** Hero, Text, Image, Cards, Statistics, Events, Gallery, Testimonials, FAQ, Contact, Form, Table, Chart, SDG Information, Sustainability Metrics, Waste Statistics, Resource Statistics, Custom Content
- **Navigation:** Add, remove, reorder, hide/show menu items
- **Themes:** Colors, fonts, button/card/header/footer styles, dark mode, custom CSS
- **Forms:** Custom forms with text, email, number, date, dropdown, radio, checkbox, textarea, file upload, rating fields
- **Site Settings:** Logo, favicon, announcements, contact info, social links, footer text

## 🔐 Security

- Spring Security with BCrypt password encoding
- Three roles: `ROLE_ADMIN`, `ROLE_ORGANIZER`, `ROLE_PARTICIPANT`
- Role-based route protection
- Method-level security with `@EnableMethodSecurity`
- CSRF protection (disabled for development)

## 📈 Reports

- Event sustainability reports in PDF format
- Includes event info, attendance, resource consumption, waste data, sustainability score, recommendations
- Accessible from organizer dashboard and event details page

## 📁 Project Structure

```
ecoevent/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/ecoevent/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   ├── service/
│   │   │   ├── util/
│   │   │   └── EcoEventApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/css/style.css
│   │       ├── static/js/app.js
│   │       └── templates/
│   │           ├── fragments/
│   │           ├── admin/
│   │           ├── organizer/
│   │           ├── participant/
│   │           ├── dynamic/
│   │           ├── error/
│   │           ├── home.html
│   │           ├── login.html
│   │           ├── register.html
│   │           ├── events.html
│   │           ├── event-details.html
│   │           ├── about.html
│   │           ├── gallery.html
│   │           ├── sdg12.html
│   │           └── contact.html
│   └── test/
└── ...
```

## ⚠️ Security Note

This application uses demo credentials for testing purposes. In any real deployment:
1. Change all default passwords
2. Enable CSRF protection
3. Use environment variables for database credentials
4. Configure proper email settings
5. Use HTTPS

## 📝 License

This is an academic project for Java assessment purposes.

## 🤝 Contributing

This is an assessment project. Feel free to extend and improve.
