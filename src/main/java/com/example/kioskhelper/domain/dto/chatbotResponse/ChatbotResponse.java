package com.example.kioskhelper.domain.dto.chatbotResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotResponse {
    private String version;
    private String userId;
    private String sessionId;
    private long timestamp;
    private List<Bubble> bubbles;
    private List<QuickButton> quickButtons;
    private Scenario scenario;
    private List<Entity> entities;
    private List<Keyword> keywords;
    private ConverSation conversation;
    private String normalizer;
    private PersistentMenu persistentMenu;
    private String event;

    // Getters and Setters
}

