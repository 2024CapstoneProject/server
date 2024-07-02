package com.example.kioskhelper.domain.dto.chatbotResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
// Additional classes for any other nested objects
public class Keyword {
    private String keyword;
    private String group;
    private String type;

    // Getters and Setters
}
