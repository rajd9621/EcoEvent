package com.ecoevent.util;

import com.ecoevent.entity.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PDFReportUtil {

    public byte[] generateEventReport(Event event, EventResource resource, EventWaste waste,
                                       SustainabilityScore score, int registrationCount,
                                       int presentCount, List<Feedback> feedbacks) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(46, 125, 50));
            Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(46, 125, 50));
            Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL);
            Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD);

            Paragraph title = new Paragraph("EcoEvent - Sustainability Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            Paragraph subtitle = new Paragraph("Aligned with UN SDG 12 - Responsible Consumption and Production", normalFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Event Info
            document.add(new Paragraph("Event Information", headerFont));
            document.add(Chunk.NEWLINE);
            addInfoLine(document, "Event Title:", event.getTitle(), labelFont, normalFont);
            addInfoLine(document, "Location:", event.getLocation(), labelFont, normalFont);
            addInfoLine(document, "Start Date:", event.getStartDate() != null ? event.getStartDate().toString() : "N/A", labelFont, normalFont);
            addInfoLine(document, "End Date:", event.getEndDate() != null ? event.getEndDate().toString() : "N/A", labelFont, normalFont);
            addInfoLine(document, "Expected Attendance:", String.valueOf(event.getExpectedAttendance()), labelFont, normalFont);
            addInfoLine(document, "Actual Attendance:", String.valueOf(event.getActualAttendance() != null ? event.getActualAttendance() : presentCount), labelFont, normalFont);
            addInfoLine(document, "Registrations:", String.valueOf(registrationCount), labelFont, normalFont);
            addInfoLine(document, "Organizer:", event.getOrganizer() != null ? event.getOrganizer().getFullName() : "N/A", labelFont, normalFont);
            document.add(Chunk.NEWLINE);

            // Sustainability Score
            if (score != null) {
                document.add(new Paragraph("Sustainability Score", headerFont));
                document.add(Chunk.NEWLINE);
                addInfoLine(document, "Total Score:", score.getTotalScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Grade:", score.getGrade(), labelFont, normalFont);
                addInfoLine(document, "Resource Efficiency:", score.getResourceEfficiencyScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Waste Management:", score.getWasteManagementScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Food Management:", score.getFoodManagementScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Digitalization:", score.getDigitalizationScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Reusable Materials:", score.getReusableMaterialsScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Sustainable Vendors:", score.getSustainableVendorsScore() + "/100", labelFont, normalFont);
                addInfoLine(document, "Post-event Reporting:", score.getPostEventReportingScore() + "/100", labelFont, normalFont);
                if (score.getRecommendations() != null) {
                    document.add(Chunk.NEWLINE);
                    addInfoLine(document, "Recommendations:", score.getRecommendations(), labelFont, normalFont);
                }
                document.add(Chunk.NEWLINE);
            }

            // Resource Consumption
            if (resource != null) {
                document.add(new Paragraph("Resource Consumption", headerFont));
                document.add(Chunk.NEWLINE);
                addInfoLine(document, "Food Requirement:", String.valueOf(resource.getFoodRequirement()), labelFont, normalFont);
                addInfoLine(document, "Food Consumed:", String.valueOf(resource.getFoodConsumed()), labelFont, normalFont);
                addInfoLine(document, "Food Remaining:", String.valueOf(resource.getFoodRemaining()), labelFont, normalFont);
                addInfoLine(document, "Water Consumption:", String.valueOf(resource.getWaterConsumed()), labelFont, normalFont);
                addInfoLine(document, "Electricity Usage:", String.valueOf(resource.getElectricityUsage()), labelFont, normalFont);
                addInfoLine(document, "Printed Materials:", String.valueOf(resource.getPrintedMaterials()), labelFont, normalFont);
                addInfoLine(document, "Reusable Materials:", String.valueOf(resource.getReusableMaterials()), labelFont, normalFont);
                addInfoLine(document, "Digital Invitations:", String.valueOf(resource.getDigitalInvitationsSent()), labelFont, normalFont);
                addInfoLine(document, "Digital Tickets:", String.valueOf(resource.getDigitalTicketsIssued()), labelFont, normalFont);
                document.add(Chunk.NEWLINE);
            }

            // Waste Management
            if (waste != null) {
                document.add(new Paragraph("Waste Management", headerFont));
                document.add(Chunk.NEWLINE);
                addInfoLine(document, "Organic Waste:", String.valueOf(waste.getOrganicWaste()), labelFont, normalFont);
                addInfoLine(document, "Plastic Waste:", String.valueOf(waste.getPlasticWaste()), labelFont, normalFont);
                addInfoLine(document, "Paper Waste:", String.valueOf(waste.getPaperWaste()), labelFont, normalFont);
                addInfoLine(document, "E-Waste:", String.valueOf(waste.getEWaste()), labelFont, normalFont);
                addInfoLine(document, "Recyclable Waste:", String.valueOf(waste.getRecyclableWaste()), labelFont, normalFont);
                addInfoLine(document, "Waste Recycled:", String.valueOf(waste.getWasteRecycled()), labelFont, normalFont);
                addInfoLine(document, "Total Waste Generated:", String.valueOf(waste.getTotalWasteGenerated()), labelFont, normalFont);
                addInfoLine(document, "Waste Segregation:", waste.isWasteSegregationDone() ? "Yes" : "No", labelFont, normalFont);
                document.add(Chunk.NEWLINE);
            }

            // Sustainable Practices
            document.add(new Paragraph("Sustainable Practices", headerFont));
            document.add(Chunk.NEWLINE);
            addInfoLine(document, "Digital Invitations:", event.isDigitalInvitations() ? "Yes" : "No", labelFont, normalFont);
            addInfoLine(document, "Digital Tickets:", event.isDigitalTickets() ? "Yes" : "No", labelFont, normalFont);
            addInfoLine(document, "Reusable Decorations:", event.isReusableDecorations() ? "Yes" : "No", labelFont, normalFont);
            addInfoLine(document, "Waste Segregation:", event.isWasteSegregation() ? "Yes" : "No", labelFont, normalFont);
            addInfoLine(document, "Sustainable Food:", event.isSustainableFoodPractices() ? "Yes" : "No", labelFont, normalFont);
            addInfoLine(document, "Post-event Report:", event.isPostEventReportGenerated() ? "Yes" : "No", labelFont, normalFont);
            document.add(Chunk.NEWLINE);

            // Feedback Summary
            if (feedbacks != null && !feedbacks.isEmpty()) {
                document.add(new Paragraph("Participant Feedback", headerFont));
                document.add(Chunk.NEWLINE);
                double avgRating = feedbacks.stream().filter(f -> f.getRating() != null)
                        .mapToInt(Feedback::getRating).average().orElse(0);
                addInfoLine(document, "Average Rating:", String.format("%.1f/5", avgRating), labelFont, normalFont);
                addInfoLine(document, "Total Feedback:", String.valueOf(feedbacks.size()), labelFont, normalFont);
            }

            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("Generated by EcoEvent - Smart Sustainable Event Management System", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF report: " + e.getMessage());
        }

        return baos.toByteArray();
    }

    private void addInfoLine(Document doc, String label, String value, Font labelFont, Font valueFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Phrase(label + " ", labelFont));
        p.add(new Phrase(value != null ? value : "N/A", valueFont));
        doc.add(p);
    }
}
