package com.railway.ticketsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Home page mapping
    @GetMapping("/")
    public String homePage() {
        return "index";  // This should match your file name in src/main/resources/templates/
    }

    // Example additional pages
    @GetMapping("/register")
    public String registerPage() {
    return "register";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/feedback")
    public String feedbackPage() {
        return "feedback";
    }

    @GetMapping("/notification")
    public String notificationPage() {
        return "notification";
    }
}
