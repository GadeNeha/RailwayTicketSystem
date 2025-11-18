package com.railway.ticketsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.ticketsystem.model.User;
import com.railway.ticketsystem.repository.UserRepository;
import com.railway.ticketsystem.security.PasswordHasher;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    // ✅ Register a new user
    /*public String registerUser(User user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            return "❌ Email already registered!";
        }

        // ✅ Hash password before saving
        String hashedPassword = passwordHasher.hash(user.getPassword());
        user.setPassword(hashedPassword);

        userDAO.save(user);
        System.out.println("✅ User saved with hashed password: " + hashedPassword);
        return "✅ User registered successfully: " + user.getEmail();
    }*/

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
           return "❌ Email already registered!";
        }

        // ✅ Add this null or empty password check
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
           throw new NullPointerException("Password cannot be null or empty");
        }

        if (user.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }

        String hashedPassword = passwordHasher.hash(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        System.out.println("✅ User saved with hashed password: " + hashedPassword);
        return "✅ User registered successfully: " + user.getEmail();
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String updateUserProfile(String email, User updatedInfo) {
        User user = userRepository.findByEmail(email);
        if (user == null) return "User not found!";
        user.setPhone(updatedInfo.getPhone());
        user.setAddress(updatedInfo.getAddress());
        userRepository.save(user);
        return "Profile updated successfully!";
    }

}
