package com.railway.ticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.ticketsystem.model.Notification;
import com.railway.ticketsystem.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ Send notification
    @PostMapping("/send")
    public String sendNotification(@RequestBody Notification request) {
        return notificationService.sendNotification(request.getMessage(), request.getRecipientEmail());
}


    // ✅ Get all notifications
    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // ✅ Get notifications by email
    @GetMapping("/user/{email}")
    public List<Notification> getUserNotifications(@PathVariable String email) {
        return notificationService.getNotificationsByEmail(email);
    }
}
