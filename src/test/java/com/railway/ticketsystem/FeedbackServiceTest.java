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

import com.railway.ticketsystem.model.Feedback;
import com.railway.ticketsystem.repository.FeedbackRepository;
import com.railway.ticketsystem.service.FeedbackService;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitFeedback() {
        Feedback feedback = new Feedback(1L, "neha@example.com", "Good Service", 5, LocalDateTime.now());
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        String result = feedbackService.submitFeedback(feedback);

        assertTrue(result.contains("successfully"));
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testSubmitEmptyMessage() {
        Feedback feedback = new Feedback(2L, "neha@example.com", "", 4, LocalDateTime.now());
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        String result = feedbackService.submitFeedback(feedback);

        assertTrue(result.contains("successfully"));
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testFeedbackRatingBoundaries() {
        Feedback feedbackLow = new Feedback(3L, "neha@example.com", "Low rating", 1, LocalDateTime.now());
        Feedback feedbackHigh = new Feedback(4L, "neha@example.com", "High rating", 5, LocalDateTime.now());

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedbackLow);

        feedbackService.submitFeedback(feedbackLow);
        feedbackService.submitFeedback(feedbackHigh);

        verify(feedbackRepository, times(2)).save(any(Feedback.class));
    }

    @Test
    void testGetAllFeedbacks() {
        when(feedbackRepository.findAll()).thenReturn(List.of(
                new Feedback(1L, "a@example.com", "Nice", 4, LocalDateTime.now()),
                new Feedback(2L, "b@example.com", "Good", 5, LocalDateTime.now())
        ));

        assertTrue(feedbackService.getAllFeedback().size() == 2);
        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    void testGetFeedbackByUser() {
        when(feedbackRepository.findByEmail("neha@example.com")).thenReturn(List.of(
            new Feedback(1L, "neha@example.com", "Nice service", 5, LocalDateTime.now())
        ));

        List<Feedback> feedbackList = feedbackService.getFeedbackByUser("neha@example.com");
        assertEquals(1, feedbackList.size());
        verify(feedbackRepository, times(1)).findByEmail("neha@example.com");
}

}
