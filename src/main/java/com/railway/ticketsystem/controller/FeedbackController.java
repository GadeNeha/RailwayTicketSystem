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

import com.railway.ticketsystem.model.Feedback;
import com.railway.ticketsystem.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // ✅ Submit feedback
    @PostMapping("/add")
    public String addFeedback(@RequestBody Feedback feedback) {
        return feedbackService.submitFeedback(feedback);
    }

    // ✅ Get all feedback
    @GetMapping("/all")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    // ✅ Get feedback by user email
    @GetMapping("/user/{email}")
    public List<Feedback> getUserFeedback(@PathVariable String email) {
        return feedbackService.getFeedbackByUser(email);
    }
}
