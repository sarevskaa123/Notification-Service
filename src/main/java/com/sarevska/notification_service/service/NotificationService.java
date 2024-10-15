package com.sarevska.notification_service.service;

import com.sarevska.notification_service.config.RabbitMQConfig;
import com.sarevska.notification_service.entity.Notification;
import com.sarevska.notification_service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final RabbitTemplate rabbitTemplate;


    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, RabbitTemplate rabbitTemplate, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.rabbitTemplate = rabbitTemplate;
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

        // Send the message to the notification queue
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_QUEUE, notification);
    }

}
