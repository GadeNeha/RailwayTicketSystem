package com.railway.ticketsystem.utils;

import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {

    public void sendNotification(String message) {
        // Simulate notification sending (console-based)
        System.out.println("🔔 Notification Triggered: " + message);
    }
}

