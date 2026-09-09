package com.ecoevent.service;

import com.ecoevent.entity.Notification;
import com.ecoevent.entity.User;
import com.ecoevent.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(User user, String title, String message, String type, String linkUrl) {
        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .linkUrl(linkUrl)
                .read(false)
                .build();
        return notificationRepository.save(notification);
    }

    public List<Notification> findByUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> findUnreadByUser(Long userId) {
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
    }

    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    public void markAsRead(Long id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    public void markAllAsRead(Long userId) {
        notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId).forEach(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }
}
