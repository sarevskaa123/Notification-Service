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
        // Define the JSON request body
        String jsonRequest = "{\"recipientEmail\":\"test@example.com\",\"subject\":\"Test Subject\",\"content\":\"Test Content\"}";

        // Perform POST request with JSON content
        mockMvc.perform(post("/api/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON) // Set Content-Type to application/json
                        .content(jsonRequest))  // Pass the JSON request body
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(content().string("Notification sent successfully!"));

        // Verify that the service method was called with the correct parameters
        Mockito.verify(notificationService, Mockito.times(1))
                .sendNotification("test@example.com", "Test Subject", "Test Content");
    }
}
