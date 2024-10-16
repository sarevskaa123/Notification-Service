package com.sarevska.notification_service.controller;

import com.sarevska.notification_service.entity.NotificationRequest;
import com.sarevska.notification_service.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationRequest notificationRequest) {
        notificationService.sendNotification(notificationRequest.getRecipientEmail(),
                notificationRequest.getSubject(),
                notificationRequest.getContent());
        return "Notification sent successfully!";
    }

}
