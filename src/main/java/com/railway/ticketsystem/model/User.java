package com.railway.ticketsystem.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ auto-generate user_id
    @Column(name = "user_id")
    private Long userId;

    private String username;

    private String email;

    @Column(name = "password_hash")
    private String password;

    private String phone;

    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // ✅ New Field: Role (for user/admin)
    @Column(name = "role")
    private String role = "USER"; // default role

    // ✅ Default constructor (required by JPA)
    public User() {}

    // ✅ Parameterized constructor for convenience
    public User(Long userId, String username, String email, String password, String phone, String address) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.role = "USER"; // default role
    }

    // ✅ Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ✅ New Getters and Setters for Role
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
