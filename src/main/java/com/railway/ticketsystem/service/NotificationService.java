package com.railway.ticketsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.ticketsystem.model.Notification;
import com.railway.ticketsystem.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // ✅ Send notification
    public String sendNotification(String message, String recipientEmail) {
        if (message == null || message.trim().isEmpty()) {
            return "Notification message cannot be empty!";
        }

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipientEmail(recipientEmail);
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);
        return "Notification sent successfully!";
    }

    // ✅ Get notifications by email
    public List<Notification> getNotificationsByEmail(String email) {
        return notificationRepository.findByRecipientEmail(email);
    }

    // ✅ Get all notifications (for controller)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
