package com.railway.ticketsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.ticketsystem.model.LoginRequest;
import com.railway.ticketsystem.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            if (token != null) {
                return ResponseEntity.ok("✅ Login successful for " + request.getEmail());
            } else {
                return ResponseEntity.status(401).body("❌ Invalid email or password");
            }
        } catch (Exception e) {
            e.printStackTrace(); // for debugging
            return ResponseEntity.internalServerError()
                    .body("❌ Login failed: " + e.getMessage());
        }
    }
}
