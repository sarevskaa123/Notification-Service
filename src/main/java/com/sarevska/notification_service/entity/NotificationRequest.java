package com.sarevska.notification_service.entity;

import lombok.Data;

@Data
public class NotificationRequest {
    private String recipientEmail;
    private String subject;
    private String content;
}
