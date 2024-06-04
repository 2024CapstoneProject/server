package com.example.kioskhelper.controller;


import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.service.ChatGPTService;
import com.example.kioskhelper.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.kioskhelper.auth.utils.AuthUtils.getAuthenticatedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class ChatGPTController {


    private final ChatGPTService  chatService;

    @GetMapping("/ask")
    public ResponseEntity<String> askChatbot(@RequestParam String question) throws IOException {
        User user =getAuthenticatedUser();
        System.out.println("user: "+user.getId());
        String response=chatService.getBotResponse(question,user);
        return ResponseEntity.ok(response);
    }




}
