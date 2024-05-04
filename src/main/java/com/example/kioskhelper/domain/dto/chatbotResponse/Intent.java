package com.example.kioskhelper.domain.dto.chatbotResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Intent {
    private String name;
    private double score;

    // Getters and Setters
}
