package com.railway.ticketsystem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.railway.ticketsystem.model.User;
import com.railway.ticketsystem.repository.UserRepository;
import com.railway.ticketsystem.security.PasswordHasher;
import com.railway.ticketsystem.service.UserService;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        User user = new User(0L, "Neha", "neha@example.com", "neha12345", "9876543210", "Hyderabad");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordHasher.hash(user.getPassword())).thenReturn("hashedPwd");

        String result = userService.registerUser(user);

        assertTrue(result.contains("successfully"));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUserAlreadyExists() {
        User user = new User(0L, "Neha", "neha@example.com", "neha12345", "9876543210", "Hyderabad");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        String result = userService.registerUser(user);

        assertTrue(result.contains("already"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testPasswordHashingDuringRegistration() {
        User user = new User(0L, "Neha", "neha@example.com", "neha12345", "9876543210", "Hyderabad");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordHasher.hash("neha12345")).thenReturn("encrypted");

        userService.registerUser(user);

        assertEquals("encrypted", user.getPassword());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testRegisterUserNullPassword() {
        User user = new User(0L, "Neha", "neha@example.com", null, "9876543210", "Hyderabad");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> userService.registerUser(user));
    }

    @Test
    void testRegisterUserWithShortPasswordThrowsException() {
        User user = new User(0L, "Neha", "neha@example.com", "12", "9876543210", "Hyderabad");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void testUpdateUserProfileSuccess() {
        User existingUser = new User(1L, "Neha", "neha@example.com", "hashedPwd", "9999999999", "Hyderabad");
        when(userRepository.findByEmail("neha@example.com")).thenReturn(existingUser);

        User updatedInfo = new User(1L, "Neha", "neha@example.com", "hashedPwd", "8888888888", "Bangalore");
        String result = userService.updateUserProfile("neha@example.com", updatedInfo);

        assertTrue(result.contains("updated successfully"));
        assertEquals("8888888888", existingUser.getPhone());
        assertEquals("Bangalore", existingUser.getAddress());
        verify(userRepository, times(1)).save(existingUser);
}

}
