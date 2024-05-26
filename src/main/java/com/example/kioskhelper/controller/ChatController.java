package com.example.kioskhelper.controller;

import com.example.kioskhelper.domain.dto.ChatResponse;
import com.example.kioskhelper.domain.dto.ChatRoomDto;
import com.example.kioskhelper.domain.dto.TranscriptionRequest;
import com.example.kioskhelper.domain.dto.WhisperTranscriptionResponse;
import com.example.kioskhelper.service.ChatService;
import com.example.kioskhelper.service.ChatbotProc;
import com.example.kioskhelper.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {



    private final OpenAIClientService openAIClientService;

    private final ChatbotProc chatbotService;

    private final ChatService chatService;


    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WhisperTranscriptionResponse createTranscription(@ModelAttribute TranscriptionRequest transcriptionRequest){
        return openAIClientService.createTranscription(transcriptionRequest);
    }
    @GetMapping(value = "/transcription/ask", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChatResponse createTranscriptions(@ModelAttribute TranscriptionRequest transcriptionRequest){

         String text= openAIClientService.createTranscription(transcriptionRequest).getText();
        String botResponse=chatbotService.sendMessage(text,false);
        return new ChatResponse(botResponse);
    }



    // 여기서는 예시로 고정된 응답을 반환합니다.
    // 실제 구현에서는 이 부분에 챗봇 서비스를 연동하십시오.
    @GetMapping("/ask")
    public ResponseEntity<ChatResponse> askChatbot(@RequestParam String question,
                                                   @RequestParam(value ="reset", defaultValue = "false") boolean reset) {

        if(question.equals("true")
        ){
            reset=true;
        }
        String response=chatbotService.sendMessage(question,reset);
        // 챗봇 서비스로부터 응답을 받아오는 로직을 구현
        String botResponse = response; // 예시 응답
        return ResponseEntity.ok(new ChatResponse(botResponse));
    }
    @GetMapping("/test/ask")
    public ResponseEntity<ChatResponse> askChatbotTest(@RequestParam String question) {
        // 챗봇 서비스로부터 응답을 받아오는 로직을 구현
        String botResponse = question;
        System.out.println("question: " + question);
        return ResponseEntity.ok(new ChatResponse(botResponse));
    }

    @GetMapping("/test")
    public ResponseEntity<String> askChatbotTests() {
        // 챗봇 서비스로부터 응답을 받아오는 로직을 구현

        String botResponse = "1234";
        System.out.println("question: " + botResponse);
        return ResponseEntity.ok(botResponse);
    }

    @GetMapping("list/test")
    public ResponseEntity<List<ChatRoomDto>> getChatListTest(@RequestParam(value = "userId",defaultValue = "testUser") String userId) {
        List<ChatRoomDto> chatList = chatService.getChatListTest(userId);
        return ResponseEntity.ok(chatList);
    }

    @GetMapping("list")
    public ResponseEntity<List<ChatRoomDto>> getChatList(@RequestParam(value = "userId",defaultValue = "testUser") String userId) {
        List<ChatRoomDto> chatList = chatService.getChatList(userId);
        return ResponseEntity.ok(chatList);
    }


    @PostMapping("reset")
    public ResponseEntity<Void> resetChat(@RequestParam(value = "sessionId",defaultValue = "testUser") String sessionId) {
        chatService.resetChat(sessionId);
        System.out.println("reset chat ----- "+ sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reset/all")
    public ResponseEntity<String> resetAllChat() {
        chatService.resetAllChat();
        return ResponseEntity.ok("All chat reset");
    }




}
