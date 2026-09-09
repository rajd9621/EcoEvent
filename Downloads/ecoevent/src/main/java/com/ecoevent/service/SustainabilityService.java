package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
public class SustainabilityService {

    private final SustainabilityMetricRepository metricRepository;
    private final EventResourceRepository resourceRepository;
    private final EventWasteRepository wasteRepository;
    private final SustainableVendorRepository vendorRepository;
    private final SustainabilityScoreRepository scoreRepository;

    public SustainabilityService(SustainabilityMetricRepository metricRepository,
                                  EventResourceRepository resourceRepository,
                                  EventWasteRepository wasteRepository,
                                  SustainableVendorRepository vendorRepository,
                                  SustainabilityScoreRepository scoreRepository) {
        this.metricRepository = metricRepository;
        this.resourceRepository = resourceRepository;
        this.wasteRepository = wasteRepository;
        this.vendorRepository = vendorRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<SustainabilityMetric> findAllMetrics() {
        return metricRepository.findAllByOrderByDisplayOrderAsc();
    }

    public List<SustainabilityMetric> findActiveMetrics() {
        return metricRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    public SustainabilityMetric saveMetric(SustainabilityMetric metric) {
        return metricRepository.save(metric);
    }

    public void deleteMetric(Long id) {
        metricRepository.deleteById(id);
    }

    public int getTotalWeight() {
        return findActiveMetrics().stream().mapToInt(SustainabilityMetric::getWeight).sum();
    }

    public Optional<EventResource> findResourceByEvent(Long eventId) {
        return resourceRepository.findByEventId(eventId);
    }

    public EventResource saveResource(EventResource resource) {
        if (resource.getFoodRemaining() == null && resource.getFoodRequirement() != null) {
            BigDecimal consumed = resource.getFoodConsumed() != null ? resource.getFoodConsumed() : BigDecimal.ZERO;
            resource.setFoodRemaining(resource.getFoodRequirement().subtract(consumed));
        }
        return resourceRepository.save(resource);
    }

    public Optional<EventWaste> findWasteByEvent(Long eventId) {
        return wasteRepository.findByEventId(eventId);
    }

    public EventWaste saveWaste(EventWaste waste) {
        BigDecimal total = BigDecimal.ZERO;
        if (waste.getOrganicWaste() != null) total = total.add(waste.getOrganicWaste());
        if (waste.getPlasticWaste() != null) total = total.add(waste.getPlasticWaste());
        if (waste.getPaperWaste() != null) total = total.add(waste.getPaperWaste());
        if (waste.getEWaste() != null) total = total.add(waste.getEWaste());
        if (waste.getRecyclableWaste() != null) total = total.add(waste.getRecyclableWaste());
        waste.setTotalWasteGenerated(total);
        return wasteRepository.save(waste);
    }

    public List<SustainableVendor> findAllVendors() {
        return vendorRepository.findAll();
    }

    public List<SustainableVendor> findActiveVendors() {
        return vendorRepository.findByActiveTrueOrderByIdDesc();
    }

    public SustainableVendor findVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + id));
    }

    public SustainableVendor saveVendor(SustainableVendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    public Optional<SustainabilityScore> findScoreByEvent(Long eventId) {
        return scoreRepository.findByEventId(eventId);
    }

    public SustainabilityScore calculateScore(Event event) {
        List<SustainabilityMetric> metrics = findActiveMetrics();
        int totalWeight = getTotalWeight();
        if (totalWeight == 0) totalWeight = 100;

        int resourceScore = calculateResourceScore(event);
        int wasteScore = calculateWasteScore(event);
        int foodScore = calculateFoodScore(event);
        int digitalScore = calculateDigitalScore(event);
        int reusableScore = calculateReusableScore(event);
        int vendorScore = calculateVendorScore(event);
        int reportingScore = event.isPostEventReportGenerated() ? 100 : 50;

        int totalScore = 0;
        for (SustainabilityMetric m : metrics) {
            String catName = m.getCategoryName().toLowerCase().replace(" ", "_");
            int catScore;
            if (catName.equals("resource_efficiency")) {
                catScore = resourceScore;
            } else if (catName.equals("waste_management")) {
                catScore = wasteScore;
            } else if (catName.equals("food_management")) {
                catScore = foodScore;
            } else if (catName.equals("digitalization")) {
                catScore = digitalScore;
            } else if (catName.equals("reusable_materials")) {
                catScore = reusableScore;
            } else if (catName.equals("sustainable_vendors")) {
                catScore = vendorScore;
            } else if (catName.equals("post_event_reporting")) {
                catScore = reportingScore;
            } else {
                catScore = 50;
            }
            totalScore += (catScore * m.getWeight()) / totalWeight;
        }

        String grade = getGrade(totalScore);
        String recommendations = generateRecommendations(totalScore, resourceScore, wasteScore,
                foodScore, digitalScore, reusableScore, vendorScore, reportingScore);

        SustainabilityScore score = scoreRepository.findByEventId(event.getId())
                .orElse(new SustainabilityScore());
        score.setEvent(event);
        score.setResourceEfficiencyScore(resourceScore);
        score.setWasteManagementScore(wasteScore);
        score.setFoodManagementScore(foodScore);
        score.setDigitalizationScore(digitalScore);
        score.setReusableMaterialsScore(reusableScore);
        score.setSustainableVendorsScore(vendorScore);
        score.setPostEventReportingScore(reportingScore);
        score.setTotalScore(totalScore);
        score.setGrade(grade);
        score.setRecommendations(recommendations);

        return scoreRepository.save(score);
    }

    private int calculateResourceScore(Event event) {
        Optional<EventResource> opt = findResourceByEvent(event.getId());
        if (opt.isPresent()) {
            EventResource r = opt.get();
            int score = 50;
            if (r.getReusableMaterials() != null && r.getReusableMaterials() > 0) score += 10;
            if (r.getPrintedMaterials() != null && r.getPrintedMaterials() < 50) score += 10;
            if (r.getElectricityUsage() != null && r.getElectricityUsage().compareTo(new BigDecimal("500")) < 0) score += 10;
            if (r.getLocalVendorsCount() != null && r.getLocalVendorsCount() > 0) score += 10;
            return Math.min(100, score);
        }
        return 40;
    }

    private int calculateWasteScore(Event event) {
        Optional<EventWaste> opt = findWasteByEvent(event.getId());
        if (opt.isPresent()) {
            EventWaste w = opt.get();
            int score = 50;
            if (w.isWasteSegregationDone()) score += 15;
            if (w.getWasteRecycled() != null && w.getTotalWasteGenerated() != null
                    && w.getTotalWasteGenerated().compareTo(BigDecimal.ZERO) > 0) {
                double ratio = w.getWasteRecycled().doubleValue() / w.getTotalWasteGenerated().doubleValue();
                score += (int) (ratio * 35);
            }
            return Math.min(100, score);
        }
        return 30;
    }

    private int calculateFoodScore(Event event) {
        Optional<EventResource> opt = findResourceByEvent(event.getId());
        if (opt.isPresent()) {
            EventResource r = opt.get();
            if (event.isSustainableFoodPractices()) {
                int score = 70;
                if (r.getFoodRemaining() != null && r.getFoodRequirement() != null
                        && r.getFoodRequirement().compareTo(BigDecimal.ZERO) > 0) {
                    double ratio = r.getFoodRemaining().doubleValue() / r.getFoodRequirement().doubleValue();
                    if (ratio < 0.1) score += 20;
                    else if (ratio < 0.2) score += 10;
                }
                return Math.min(100, score);
            }
            return 40;
        }
        return 40;
    }

    private int calculateDigitalScore(Event event) {
        int score = 50;
        if (event.isDigitalInvitations()) score += 15;
        if (event.isDigitalTickets()) score += 15;
        Optional<EventResource> opt = findResourceByEvent(event.getId());
        if (opt.isPresent()) {
            EventResource r = opt.get();
            if (r.getDigitalInvitationsSent() != null && r.getDigitalInvitationsSent() > 0) score += 10;
            if (r.getDigitalTicketsIssued() != null && r.getDigitalTicketsIssued() > 0) score += 10;
        }
        return Math.min(100, score);
    }

    private int calculateReusableScore(Event event) {
        int score = 40;
        if (event.isReusableDecorations()) score += 30;
        Optional<EventResource> opt = findResourceByEvent(event.getId());
        if (opt.isPresent()) {
            EventResource r = opt.get();
            if (r.getReusableMaterials() != null && r.getReusableMaterials() > 10) score += 30;
        }
        return Math.min(100, score);
    }

    private int calculateVendorScore(Event event) {
        Optional<EventResource> opt = findResourceByEvent(event.getId());
        int score = 40;
        if (opt.isPresent()) {
            EventResource r = opt.get();
            if (r.getLocalVendorsCount() != null && r.getLocalVendorsCount() > 0) score += 20;
        }
        long certifiedVendors = vendorRepository.findBySustainabilityCertifiedTrue().stream().count();
        if (certifiedVendors > 0) score += 20;
        if (event.isSustainableFoodPractices()) score += 20;
        return Math.min(100, score);
    }

    private String getGrade(int score) {
        if (score >= 90) return "A+";
        if (score >= 80) return "A";
        if (score >= 70) return "B+";
        if (score >= 60) return "B";
        if (score >= 50) return "C";
        if (score >= 40) return "D";
        return "F";
    }

    private String generateRecommendations(int total, int resource, int waste, int food,
                                            int digital, int reusable, int vendor, int reporting) {
        List<String> recs = new ArrayList<>();
        if (resource < 70) recs.add("Reduce printed materials and increase digital resources");
        if (waste < 70) recs.add("Implement better waste segregation and increase recycling rates");
        if (food < 70) recs.add("Adopt sustainable food practices and minimize food waste");
        if (digital < 70) recs.add("Increase use of digital invitations and tickets");
        if (reusable < 70) recs.add("Use reusable decorations and materials");
        if (vendor < 70) recs.add("Partner with more local and sustainability-certified vendors");
        if (reporting < 80) recs.add("Generate comprehensive post-event sustainability reports");
        if (recs.isEmpty()) recs.add("Excellent sustainability practices! Continue maintaining high standards.");
        return String.join("; ", recs);
    }

    public Double getAverageScore() {
        return scoreRepository.getAverageScore();
    }

    public List<SustainabilityScore> findAllScores() {
        return scoreRepository.findAllByOrderByTotalScoreDesc();
    }
}
