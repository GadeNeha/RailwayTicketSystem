package com.railway.ticketsystem;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.railway.ticketsystem.model.Notification;
import com.railway.ticketsystem.repository.NotificationRepository;
import com.railway.ticketsystem.service.NotificationService;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification() {
        Notification notification = new Notification(1L, "neha@example.com", "Train delayed", LocalDateTime.now());
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        String result = notificationService.sendNotification("Train delayed", "neha@example.com");

        assertTrue(result.contains("successfully"));
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendNotificationEmptyMessage() {
        Notification notification = new Notification(2L, "neha@example.com", "", LocalDateTime.now());
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        String result = notificationService.sendNotification("", "neha@example.com");

        assertTrue(result.contains("cannot be empty"));
        verify(notificationRepository, times(0)).save(any(Notification.class));
    }

    @Test
    void testGetNotificationsByEmail() {
        when(notificationRepository.findByRecipientEmail("neha@example.com")).thenReturn(List.of(
                new Notification(1L, "neha@example.com", "Hi", LocalDateTime.now()),
                new Notification(2L, "neha@example.com", "Reminder", LocalDateTime.now())
        ));

        assertTrue(notificationService.getNotificationsByEmail("neha@example.com").size() == 2);
        verify(notificationRepository, times(1)).findByRecipientEmail("neha@example.com");
    }

    @Test
    void testMultipleNotifications() {
        Notification n1 = new Notification(1L, "neha@example.com", "Msg 1", LocalDateTime.now());
        Notification n2 = new Notification(2L, "neha@example.com", "Msg 2", LocalDateTime.now());

        when(notificationRepository.save(any(Notification.class))).thenReturn(n1, n2);

        notificationService.sendNotification("Msg 1", "neha@example.com");
        notificationService.sendNotification("Msg 2", "neha@example.com");

        verify(notificationRepository, times(2)).save(any(Notification.class));
    }

    @Test
    void testGetAllNotifications() {
        when(notificationRepository.findAll()).thenReturn(List.of(
            new Notification(1L, "a@example.com", "Msg1", LocalDateTime.now()),
            new Notification(2L, "b@example.com", "Msg2", LocalDateTime.now())
        ));

        List<Notification> notifications = notificationService.getAllNotifications();
        assertEquals(2, notifications.size());
        verify(notificationRepository, times(1)).findAll();
}

}
