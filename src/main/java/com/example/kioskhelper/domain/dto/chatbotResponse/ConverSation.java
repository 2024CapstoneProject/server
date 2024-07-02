package com.example.kioskhelper.domain.dto.chatbotResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@lombok.Data
@Getter
@Setter
public class ConverSation {
    private String senarioName;
    private String chatUtteranceSetId;
    private List<String> types;
}
