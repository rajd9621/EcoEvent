package com.ecoevent.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer expectedAttendance;
    private BigDecimal ticketPrice;
    private Integer maxParticipants;
    private String imageUrl;
    private Long categoryId;
    private boolean digitalInvitations = true;
    private boolean digitalTickets = true;
    private boolean reusableDecorations;
    private boolean wasteSegregation;
    private boolean sustainableFoodPractices;
}
