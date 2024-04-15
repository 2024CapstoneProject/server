package com.example.kioskhelper.domain.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class FcmRequestDto {
    private String token;
    private String title;
    private String body;
}
