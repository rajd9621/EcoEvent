package com.ecoevent.service;

import com.ecoevent.entity.Feedback;
import com.ecoevent.repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found: " + id));
    }

    public List<Feedback> findByEvent(Long eventId) {
        return feedbackRepository.findByEventId(eventId);
    }

    public List<Feedback> findApprovedFeedback() {
        return feedbackRepository.findByApprovedTrueOrderByCreatedAtDesc();
    }

    public List<Feedback> findFeaturedFeedback() {
        return feedbackRepository.findByApprovedTrueAndFeaturedTrueOrderByCreatedAtDesc();
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public Feedback approve(Long id) {
        Feedback f = findById(id);
        f.setApproved(true);
        return feedbackRepository.save(f);
    }

    public Feedback toggleFeatured(Long id) {
        Feedback f = findById(id);
        f.setFeatured(!f.isFeatured());
        return feedbackRepository.save(f);
    }

    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }
}
