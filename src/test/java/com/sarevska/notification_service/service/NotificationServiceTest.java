package com.sarevska.notification_service.service;

import com.sarevska.notification_service.entity.Notification;
import com.sarevska.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;

    @BeforeEach
    public void setUp() {
        notification = new Notification();
        notification.setRecipientEmail("test@example.com");
        notification.setSubject("Test Subject");
        notification.setContent("Test Content");
        notification.setSentAt(LocalDateTime.now());
    }

    @Test
    public void testSendNotification_savesNotificationAndSendsToQueue() {
        notificationService.sendNotification(notification.getRecipientEmail(), notification.getSubject(), notification.getContent());

        verify(notificationRepository, times(1)).save(any(Notification.class));

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(rabbitTemplate, times(1)).convertAndSend(any(String.class), notificationCaptor.capture());

        Notification capturedNotification = notificationCaptor.getValue();
        assertNotNull(capturedNotification.getSentAt());
        assertEquals("test@example.com", capturedNotification.getRecipientEmail());
        assertEquals("Test Subject", capturedNotification.getSubject());
        assertEquals("Test Content", capturedNotification.getContent());
    }
}
