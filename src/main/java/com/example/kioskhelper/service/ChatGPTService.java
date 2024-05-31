package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.dto.ChatResponseDTO;
import com.example.kioskhelper.domain.entity.Chat;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.domain.etc.ChatbotResponse;
import com.example.kioskhelper.domain.etc.Menu;
import com.example.kioskhelper.repository.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGPTService {
    @Value("${openai-service.api-key}")
    private  String openaiApiKey;

    private final ChatRepository chatRepository;


   public String getBotResponse(String userMessage, User user) {


    List<Chat> recentMessages = chatRepository.findRecentChatsByUserIdAndExpiredFalse(user, PageRequest.of(0, 5));

            //chatRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "timestamp"))).getContent();

    // Extract keywords from user message
    String keywords = extractKeywords(userMessage);

    // Create the system message based on extracted keywords
    String systemMessage = keywords;//generateSystemMessage(keywords);


    // Create the message list for ChatGPT API
    List<Map<String, String>> messages = new ArrayList<>();

    // Add dynamic system message for context setting
    messages.add(Map.of("role", "system", "content", systemMessage));

    // Add recent chat history
    for (int i = recentMessages.size() - 1; i >= 0; i--) {
        Chat message = recentMessages.get(i);
        messages.add(Map.of("role", "user", "content", message.getMessage()));
        messages.add(Map.of("role", "assistant", "content", message.getResponse()));
    }

    // Add current user message
    messages.add(Map.of("role", "user", "content", userMessage));

    // Call ChatGPT API
    RestTemplate restTemplate = new RestTemplate();
    String apiUrl = "https://api.openai.com/v1/chat/completions";
    String apiKey = openaiApiKey;

    // Create request payload
    Map<String, Object> payload = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", messages
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + apiKey);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        System.out.println("request: "+request);
    ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
    // Parse response and extract the bot's message
    String botMessage = extractBotMessageFromResponse(response.getBody());

    return botMessage;
}

    private String extractKeywords(String userMessage) {
        // Simple keyword extraction logic (can be replaced with a more sophisticated NLP model)
        if (userMessage.contains("버거킹")||Menu.contains(userMessage,"버거킹")) {
            return ChatbotResponse.BurgerKing.getKey();
        }
        else if(userMessage.contains("맥도날드")||Menu.contains(userMessage,"맥도날드"))
        {
            return ChatbotResponse.Mc.getKey();
        }

        return ChatbotResponse.Normal.getKey();
    }

    private String generateSystemMessage(String keywords) {
        // Generate specific system message based on extracted keywords
        if (keywords.contains("Burger King")) {
            return "You are a helpful assistant with expertise in Burger King menus. Provide detailed information about various menu items, including burgers, fries, and drinks.";
        }
        return "You are a helpful assistant.";
    }

    private String extractBotMessageFromResponse(String responseBody) {
    ObjectMapper mapper = new ObjectMapper();
    try {
        ChatResponseDTO chatResponse = mapper.readValue(responseBody, ChatResponseDTO.class);
        if (chatResponse.getChoices() != null && !chatResponse.getChoices().isEmpty()) {
            return chatResponse.getChoices().get(0).getMessage().getContent();
        } else {
            return "Sorry, I couldn't understand that.";
        }
    } catch (JsonProcessingException e) {
        e.printStackTrace();
        return "Sorry, I couldn't understand that.";
    }
}

    public List<Chat> getChatHistory() {
        return chatRepository.findAll();
    }

}
