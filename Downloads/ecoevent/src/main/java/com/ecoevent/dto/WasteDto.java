package com.ecoevent.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WasteDto {
    private Long eventId;
    private BigDecimal organicWaste;
    private BigDecimal plasticWaste;
    private BigDecimal paperWaste;
    private BigDecimal eWaste;
    private BigDecimal recyclableWaste;
    private BigDecimal wasteRecycled;
    private boolean wasteSegregationDone;
}
