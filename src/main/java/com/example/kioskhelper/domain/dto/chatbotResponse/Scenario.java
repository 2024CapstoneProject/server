package com.example.kioskhelper.domain.dto.chatbotResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class Scenario {
    private String name;
    private String chatUtteranceSetId;
    private List<Intent> intents;

    // Getters and Setters
}
