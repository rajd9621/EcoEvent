-- ============================================
-- EcoEvent - Sample Data for MySQL Setup
-- Run after JPA creates tables (ddl-auto=update)
-- ============================================

-- Demo Users (passwords are BCrypt encoded "demo123")
-- In production, use environment variables and proper password management

INSERT INTO users (full_name, email, password, role, enabled, phone, created_at, updated_at)
VALUES 
('Admin User', 'admin@ecoevent.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MQDqKZ1a0zJpbcJxJqJKQhJjJzJjJj', 'ROLE_ADMIN', true, '9999999999', NOW(), NOW()),
('Event Organizer', 'organizer@ecoevent.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MQDqKZ1a0zJpbcJxJqJKQhJjJzJjJj', 'ROLE_ORGANIZER', true, '8888888888', NOW(), NOW()),
('John Participant', 'participant@ecoevent.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MQDqKZ1a0zJpbcJxJqJKQhJjJzJjJj', 'ROLE_PARTICIPANT', true, '7777777777', NOW(), NOW());

-- Event Categories
INSERT INTO event_categories (name, description, icon_class, color_code, active, created_at)
VALUES
('Conference', 'Professional conferences and summits', 'bi-building', '#2E7D32', true, NOW()),
('Workshop', 'Hands-on workshops and training', 'bi-tools', '#1565C0', true, NOW()),
('Festival', 'Community festivals and celebrations', 'bi-music-note-beamed', '#E65100', true, NOW()),
('Webinar', 'Online webinars and virtual events', 'bi-laptop', '#6A1B9A', true, NOW());

-- Note: The DataInitializer.java handles all demo data seeding automatically.
-- This SQL file is provided for manual MySQL setup if needed.
