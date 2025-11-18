package com.railway.ticketsystem.utils;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    public void sendEmail(String to, String subject, String body) {
        // Simulate email sending (mocked for now)
        System.out.println("📧 Sending Email...");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("✅ Email sent successfully!");
    }
}
