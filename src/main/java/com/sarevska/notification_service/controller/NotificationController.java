package com.sarevska.notification_service.controller;

import com.sarevska.notification_service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String recipientEmail,
                                   @RequestParam String subject,
                                   @RequestParam String content) {
        notificationService.sendNotification(recipientEmail, subject, content);
        return "Notification sent successfully!";
    }

}
