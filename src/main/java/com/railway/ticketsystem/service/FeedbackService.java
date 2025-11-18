package com.railway.ticketsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.ticketsystem.model.Feedback;
import com.railway.ticketsystem.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public String submitFeedback(Feedback feedback) {
        if (feedback.getSubmittedAt() == null) {
            feedback.setSubmittedAt(LocalDateTime.now()); // ✅ auto-fill timestamp
        }
        feedbackRepository.save(feedback);
        return "✅ Feedback submitted successfully! Thank you for your response.";
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbackByUser(String email) {
        return feedbackRepository.findByEmail(email);
    }
}
