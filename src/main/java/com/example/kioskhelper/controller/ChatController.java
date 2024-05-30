package com.example.kioskhelper.controller;

import com.example.kioskhelper.auth.utils.AuthUtils;
import com.example.kioskhelper.domain.dto.ChatResponse;
import com.example.kioskhelper.domain.dto.ChatRoomDto;
import com.example.kioskhelper.domain.dto.TranscriptionRequest;
import com.example.kioskhelper.domain.dto.WhisperTranscriptionResponse;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.service.ChatService;
import com.example.kioskhelper.service.ChatbotProc;
import com.example.kioskhelper.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kioskhelper.auth.utils.AuthUtils.getAuthenticatedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {



    private final OpenAIClientService openAIClientService;

    private final ChatbotProc chatbotService;

    private final ChatService chatService;






    // 여기서는 예시로 고정된 응답을 반환합니다.
    // 실제 구현에서는 이 부분에 챗봇 서비스를 연동하십시오.
    @GetMapping("/ask")
    public ResponseEntity<ChatResponse> askChatbot(@RequestParam String question,
                                                   @RequestParam(value ="reset", defaultValue = "false") boolean reset) {

        if(question.equals("true")
        ){
            reset=true;
        }
        User user =getAuthenticatedUser();
        String response=chatbotService.sendMessage(user,question,reset);
        // 챗봇 서비스로부터 응답을 받아오는 로직을 구현
        String botResponse = response; // 예시 응답
        return ResponseEntity.ok(new ChatResponse(botResponse));
    }
    @GetMapping("/list/test")
    public ResponseEntity<List<ChatRoomDto>> getChatListTest() {
        User user =getAuthenticatedUser();
        String userId = user.getUid();
        List<ChatRoomDto> chatList = chatService.getChatListTest(userId);
        return ResponseEntity.ok(chatList);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDto>> getChatList() {
        User user =getAuthenticatedUser();
        List<ChatRoomDto> chatList = chatService.getChatList(user);
        return ResponseEntity.ok(chatList);
    }

    @PostMapping("reset")
    public ResponseEntity<Void> resetChat() {
        User user =getAuthenticatedUser();
        chatService.resetChat(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reset/all")
    public ResponseEntity<String> resetAllChat() {
        chatService.resetAllChat();
        return ResponseEntity.ok("All chat reset");
    }


}
