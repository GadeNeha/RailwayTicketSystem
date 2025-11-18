package com.railway.ticketsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.railway.ticketsystem.model.User;
import com.railway.ticketsystem.repository.UserRepository;
import com.railway.ticketsystem.security.JwtUtils;
import com.railway.ticketsystem.security.PasswordHasher;
import com.railway.ticketsystem.service.AuthService;

class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordHasher passwordHasher;
    @Mock private JwtUtils jwtUtils;

    @InjectMocks private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        User mockUser = new User(1L, "Neha", "neha@example.com", "hashed123", "9999999999", "Hyderabad");
        when(userRepository.findByEmail("neha@example.com")).thenReturn(mockUser);
        when(passwordHasher.matches("neha123", "hashed123")).thenReturn(true);
        when(jwtUtils.generateToken("neha@example.com")).thenReturn("mockToken");

        String result = authService.login("neha@example.com", "neha123");

        assertEquals("mockToken", result);
    }

    @Test
    void testLoginInvalidPassword() {
        User mockUser = new User(1L, "Neha", "neha@example.com", "hashed123", "9999999999", "Hyderabad");
        when(userRepository.findByEmail("neha@example.com")).thenReturn(mockUser);
        when(passwordHasher.matches("wrongpass", "hashed123")).thenReturn(false);

        assertNull(authService.login("neha@example.com", "wrongpass"));
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        assertNull(authService.login("unknown@example.com", "any"));
    }

    @Test
    void testLoginEmptyEmail() {
        assertNull(authService.login("", "pass"));
    }

    @Test
    void testLoginNullPassword() {
        User mockUser = new User(1L, "Neha", "neha@example.com", "hashed123", "9999999999", "Hyderabad");
        when(userRepository.findByEmail("neha@example.com")).thenReturn(mockUser);

        assertNull(authService.login("neha@example.com", null));
    }

    @Test
    void testForgotPasswordSuccess() {
        User mockUser = new User(1L, "Neha", "neha@example.com", "oldHashed", "9999999999", "Hyderabad");
        when(userRepository.findByEmail("neha@example.com")).thenReturn(mockUser);
        when(passwordHasher.hash("newPassword")).thenReturn("newHashed");

        String result = authService.resetPassword("neha@example.com", "newPassword");

        assertEquals("Password reset successfully!", result);
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testAdminLoginRoleVerification() {
        User adminUser = new User(1L, "Admin", "admin@railway.com", "hashedAdmin", "0000000000", "HQ");
        adminUser.setRole("ADMIN");

        when(userRepository.findByEmail("admin@railway.com")).thenReturn(adminUser);
        when(passwordHasher.matches("admin123", "hashedAdmin")).thenReturn(true);
        when(jwtUtils.generateToken("admin@railway.com")).thenReturn("adminToken");

        String result = authService.login("admin@railway.com", "admin123");

        assertEquals("adminToken", result);
        assertEquals("ADMIN", adminUser.getRole());
    }


}
