package com.ecoevent.controller;

import com.ecoevent.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/event/{id}/pdf")
    public void downloadEventReport(@PathVariable Long id, HttpServletResponse response) throws Exception {
        byte[] pdf = reportService.generateEventSustainabilityReport(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=event-report-" + id + ".pdf");
        response.getOutputStream().write(pdf);
        response.getOutputStream().flush();
    }
}
