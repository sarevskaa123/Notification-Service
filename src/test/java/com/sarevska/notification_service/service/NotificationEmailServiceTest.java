package com.sarevska.notification_service.service;

import com.sarevska.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationEmailServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationEmailService notificationEmailService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testSendEmail() {
        String recipientEmail = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        notificationEmailService.sendEmail(recipientEmail, subject, content);

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(recipientEmail);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(content);

        verify(mailSender).send(expectedMessage);
    }
}
