package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.dto.ChatRoomDto;
import com.example.kioskhelper.domain.entity.Chat;
import com.example.kioskhelper.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    public void saveChat(String userId, String voiceMessage, String chatbotMessage) {
        Chat chat = Chat.builder()
                .sessionId(userId)
                .message(voiceMessage)
                .response(chatbotMessage)
                .createdAt(java.time.LocalDateTime.now())
                .expired(false)
                .build();

        chatRepository.save(chat);
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

    public List<ChatRoomDto> getChatList(String userId) {
         List<Chat> chatList = chatRepository.findBySessionId(userId);
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

    public void resetChat(String sessionId) {
        List<Chat> chatList = chatRepository.findBySessionId(sessionId);
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
