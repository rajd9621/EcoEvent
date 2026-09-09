package com.ecoevent.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackDto {
    @NotNull(message = "Event is required")
    private Long eventId;
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    @NotBlank(message = "Comment is required")
    private String comment;
    @Min(1) @Max(5)
    private Integer sustainabilityRating;
}
