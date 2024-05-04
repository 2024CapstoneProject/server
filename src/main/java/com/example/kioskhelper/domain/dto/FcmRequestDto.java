package com.example.kioskhelper.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FcmRequestDto {
    private String token;
    private String title;
    private String body;
}
