package com.sarevska.notification_service.listener;

import com.sarevska.notification_service.config.RabbitMQConfig;
import com.sarevska.notification_service.entity.Notification;
import com.sarevska.notification_service.service.NotificationEmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationEmailService notificationEmailService;

    public NotificationListener(NotificationEmailService notificationEmailService) {
        this.notificationEmailService = notificationEmailService;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void receiveNotification(Notification notificationMessage) {
        // Send email when the message is received
        notificationEmailService.sendEmail(notificationMessage.getRecipientEmail(), notificationMessage.getSubject(), notificationMessage.getContent());
    }
}
