package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.dto.FcmRequestDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import com.google.firebase.messaging.Notification;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization() {
        try {
            // ClassPathResource를 사용하여 serviceAccountKey.json 파일의 InputStream을 얻습니다.
            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            InputStream serviceAccount = resource.getInputStream();

            // FirebaseOptions 객체를 생성합니다.
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Firebase 앱을 초기화합니다.
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLocationRequestPushNotification(FcmRequestDto dto) {
        String token = dto.getToken();
        String title = dto.getTitle();
        String body = dto.getBody();
        // 데이터 메시지에 위치 정보 요청 타입 추가
        Map<String, String> dataPayload = new HashMap<>();
        dataPayload.put("type", "LOCATION_REQUEST");

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(dataPayload) // 데이터 메시지 포함
                .build();

        FirebaseMessaging.getInstance().sendAsync(message).addListener(() -> {
            System.out.println("Location request message has been sent.");
        }, Executors.newSingleThreadExecutor());
    }




}