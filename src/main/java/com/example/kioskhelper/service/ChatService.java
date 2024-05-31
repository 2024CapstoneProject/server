package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.dto.ChatRoomDto;
import com.example.kioskhelper.domain.entity.Chat;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.repository.ChatRepository;
import com.example.kioskhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatGPTService chatGPTService;

    private String answer="이 챗봇은 카페, 맥도날드, 버거킹 키오스크와 관련된 챗봇입니다.";

    public String saveChat(User user,String sessionId, String voiceMessage, String chatbotMessage) {


       if(chatbotMessage.contains(answer))
       {
           chatbotMessage= chatGPTService.getBotResponse(voiceMessage, user);
       }


        Chat chat = Chat.builder()
                .user(user)
                .sessionId(sessionId)
                .message(voiceMessage)
                .response(chatbotMessage)
                .createdAt(java.time.LocalDateTime.now())
                .expired(false)
                .build();

        chatRepository.save(chat);
        return chatbotMessage;
    }

    public List<ChatRoomDto> getChatListTest(String userId) {
       // List<Chat> chatList = chatRepository.findBySessionId(userId);
        List<Chat> chatList = chatRepository.findNoExpiredChat();
        return chatList.stream().map(chat -> {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setMessage(chat.getMessage());
            chatRoomDto.setResponse(chat.getResponse());
            chatRoomDto.setCreatedAt(chat.getCreatedAt());
            chatRoomDto.setSessionId(chat.getSessionId());
            return chatRoomDto;
        }).toList();
    }

    public List<ChatRoomDto> getChatList(User user) {
         List<Chat> chatList = chatRepository.findByUser(user);
        //List<Chat> chatList = chatRepository.findAll();
        return chatList.stream().map(chat -> {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setMessage(chat.getMessage());
            chatRoomDto.setResponse(chat.getResponse());
            chatRoomDto.setCreatedAt(chat.getCreatedAt());
            chatRoomDto.setSessionId(chat.getSessionId());
            return chatRoomDto;
        }).toList();
    }

    public void resetChat(User user) {
        List<Chat> chatList = chatRepository.findByUser(user);
        chatList.forEach(chat -> {
            chat.setExpired(true);
        });
        chatRepository.saveAll(chatList);
    }

    public void resetAllChat()
    {
        List<Chat> chatList = chatRepository.findAll();
        chatList.forEach(chat -> {
            chat.setExpired(true);
        });
        chatRepository.saveAll(chatList);
    }
}
