package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.dto.chatbotResponse.ChatbotResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Timestamp;
import java.util.Date;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class ChatbotProc {
    @Value("${chatbot.api.url}")
    String apiURL;
    @Value("${chatbot.api.key}")
    String secretKey;

    public String sendMessage(String voiceMessage) {
        String chatbotMessage = "";

        try {
            URL url = new URL(apiURL);
            String message = getReqMessage(voiceMessage);
            String encodeBase64String = makeSignature(message, secretKey);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; UTF-8");
            con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(message.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                ChatbotResponse response = mapper.readValue(con.getInputStream(), ChatbotResponse.class);
                StringBuilder descriptions = new StringBuilder();
                response.getBubbles().forEach(bubble -> {
                    if (bubble.getData() != null && bubble.getData().getDescription() != null) {
                        descriptions.append(bubble.getData().getDescription()).append("\n");
                    }
                });
                chatbotMessage = descriptions.toString();
                System.out.println("Description extracted: " + chatbotMessage);
            } else {
                chatbotMessage = "Error: " + readErrorResponse(con);
                System.out.println("Error response: " + chatbotMessage);
            }
        } catch (Exception e) {
            System.out.println("Error in sendMessage: " + e.getMessage());
        }

        return chatbotMessage;
    }

    private String readErrorResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        StringBuilder errorResponse = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            errorResponse.append(line);
        }
        in.close();
        return errorResponse.toString();
    }

    public static String makeSignature(String message, String secretKey) {
        String encodeBase64String = "";
        try {
            byte[] secret_key_bytes = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec signingKey = new SecretKeySpec(secret_key_bytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            System.out.println("Error in makeSignature: " + e.getMessage());
        }
        return encodeBase64String;
    }

    public static String getReqMessage(String voiceMessage) {
        String requestBody = "";
        try {
            JSONObject obj = new JSONObject();
            long timestamp = System.currentTimeMillis();
            obj.put("version", "v2");
            obj.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
            obj.put("timestamp", timestamp);

            JSONObject bubbles_obj = new JSONObject();
            bubbles_obj.put("type", "text");

            JSONObject data_obj = new JSONObject();
            data_obj.put("description", voiceMessage);

            bubbles_obj.put("data", data_obj);
            JSONArray bubbles_array = new JSONArray();
            bubbles_array.put(bubbles_obj);
            obj.put("bubbles", bubbles_array);
            obj.put("event", "send");

            requestBody = obj.toString();
            System.out.println("Generated request body: " + requestBody);
        } catch (Exception e) {
            System.out.println("Error in getReqMessage: " + e.getMessage());
        }
        return requestBody;
    }

}