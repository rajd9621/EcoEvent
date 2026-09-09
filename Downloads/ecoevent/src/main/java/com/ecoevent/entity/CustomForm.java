package com.ecoevent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "custom_forms")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CustomForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Form name is required")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "submit_button_text")
    private String submitButtonText = "Submit";

    @Column(name = "success_message")
    private String successMessage = "Thank you for your submission!";

    @Column(name = "target_page_slug")
    private String targetPageSlug;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "send_email_notification")
    private boolean sendEmailNotification = false;

    @Column(name = "notification_email")
    private String notificationEmail;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "customForm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    private List<FormField> fields = new ArrayList<>();

    @OneToMany(mappedBy = "customForm", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<FormSubmission> submissions = new ArrayList<>();
}
