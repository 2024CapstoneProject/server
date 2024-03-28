package com.example.kioskhelper.controller;

import com.example.kioskhelper.domain.dto.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    // 여기서는 예시로 고정된 응답을 반환합니다.
    // 실제 구현에서는 이 부분에 챗봇 서비스를 연동하십시오.
    @GetMapping("/ask")
    public ChatResponse askChatbot(@RequestParam String question) {
        // 챗봇 서비스로부터 응답을 받아오는 로직을 구현
        String botResponse = "여기에 AI 챗봇의 응답을 삽입하세요."; // 예시 응답
        return new ChatResponse(botResponse);
    }
    @GetMapping("/test/ask")
    public ChatResponse askChatbotTest(@RequestParam String response) {
        // 챗봇 서비스로부터 응답을 받아오는 로직을 구현
        String botResponse = response;
        return new ChatResponse(botResponse);
    }
}
