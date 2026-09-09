package com.ecoevent.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ResourceDto {
    private Long eventId;
    private BigDecimal foodRequirement;
    private BigDecimal foodConsumed;
    private BigDecimal waterRequirement;
    private BigDecimal waterConsumed;
    private BigDecimal electricityUsage;
    private Integer printedMaterials;
    private Integer reusableMaterials;
    private BigDecimal plasticUsage;
    private BigDecimal paperUsage;
    private Integer localVendorsCount;
    private Integer digitalInvitationsSent;
    private Integer digitalTicketsIssued;
}
