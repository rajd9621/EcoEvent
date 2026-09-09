package com.ecoevent.service;

import com.ecoevent.entity.*;
import com.ecoevent.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventCategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository, EventCategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found: " + id));
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findPublishedEvents() {
        return eventRepository.findByPublishedTrueOrderByStartDateAsc();
    }

    public List<Event> findUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    public List<Event> findPastEvents() {
        return eventRepository.findPastEvents(LocalDateTime.now());
    }

    public List<Event> findByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerIdOrderByCreatedAtDesc(organizerId);
    }

    public List<Event> search(String keyword) {
        return eventRepository.searchEvents(keyword != null ? keyword : "");
    }

    public List<Event> findByCategory(Long categoryId) {
        return eventRepository.findByCategoryId(categoryId);
    }

    public List<Event> findSustainableEvents() {
        return eventRepository.findBySustainableTrueAndPublishedTrue();
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event publishEvent(Long id) {
        Event event = findById(id);
        event.setPublished(true);
        return eventRepository.save(event);
    }

    public Event unpublishEvent(Long id) {
        Event event = findById(id);
        event.setPublished(false);
        return eventRepository.save(event);
    }

    public long countSustainableEvents() {
        return eventRepository.countSustainableEvents();
    }

    public long countTotalEvents() {
        return eventRepository.count();
    }

    public List<EventCategory> findAllCategories() {
        return categoryRepository.findByActiveTrueOrderByCreatedAtAsc();
    }

    public EventCategory saveCategory(EventCategory category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
