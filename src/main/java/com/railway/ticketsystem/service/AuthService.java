package com.railway.ticketsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.ticketsystem.model.User;
import com.railway.ticketsystem.repository.UserRepository;
import com.railway.ticketsystem.security.JwtUtils;
import com.railway.ticketsystem.security.PasswordHasher;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    @Autowired
    private JwtUtils jwtUtils;

    public String login(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return null;
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("❌ No user found for: " + email);
            return null;
        }

        if (!passwordHasher.matches(password, user.getPassword())) {
            System.out.println("❌ Invalid password for: " + email);
            return null;
        }

        System.out.println("✅ Login successful for: " + email);
        return jwtUtils.generateToken(email);
    }
    public String resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "User not found!";
        }
        user.setPassword(passwordHasher.hash(newPassword));
        userRepository.save(user);
        return "Password reset successfully!";
    }

}
