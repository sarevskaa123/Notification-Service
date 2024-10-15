package com.sarevska.notification_service.service;

import com.sarevska.notification_service.entity.Notification;
import com.sarevska.notification_service.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public void sendNotification(String recipientEmail, String subject, String content) {
        Notification notification = new Notification();
        notification.setRecipientEmail(recipientEmail);
        notification.setSubject(subject);
        notification.setContent(content);
        notification.setSentAt(LocalDateTime.now());

        // Save the notification in the database
        notificationRepository.save(notification);

        // Send email notification
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
