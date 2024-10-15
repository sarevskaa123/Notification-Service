package com.sarevska.notification_service.controller;

import com.sarevska.notification_service.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        doNothing().when(notificationService).sendNotification(anyString(), anyString(), anyString());
    }

    @Test
    public void testSendNotification_ReturnsSuccessMessage() throws Exception {
        mockMvc.perform(post("/api/notifications/send")
                        .param("recipientEmail", "test@example.com")
                        .param("subject", "Test Subject")
                        .param("content", "Test Content")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully!"));

        Mockito.verify(notificationService, Mockito.times(1))
                .sendNotification("test@example.com", "Test Subject", "Test Content");
    }
}

