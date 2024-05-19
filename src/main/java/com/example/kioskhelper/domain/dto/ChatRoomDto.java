package com.example.kioskhelper.domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomDto {

    private String message;
    private String response;
    private LocalDateTime createdAt;
    private String sessionId;

}
