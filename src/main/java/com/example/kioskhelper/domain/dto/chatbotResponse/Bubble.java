package com.example.kioskhelper.domain.dto.chatbotResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@lombok.Data
@Getter
@Setter
public class Bubble {
    private String type;
    private Data data;
    private List<Information> information;
    private List<Context> context;

    // Getters and Setters
}
