package com.railway.ticketsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Hash plain password before saving
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // ✅ Compare raw password with stored hashed password
    public boolean matches(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}

