package com.example.kioskhelper.controller;


import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.repository.UserRepository;
import com.example.kioskhelper.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

   private final EncryptionService encryptionService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String uid, @RequestParam String email) {
        System.out.println("uid: " + uid);
        System.out.println("email: " + email);

        String encryptedUid = encryptionService.encrypt(uid);
        String encryptedEmail = encryptionService.encrypt(email);
        if(userRepository.findByName(encryptedUid) != null){
            return ResponseEntity.badRequest().build();
        }
        User user = new User(encryptedUid, encryptedEmail);
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("encryptedUid", encryptedUid);
        return ResponseEntity.ok(response);
    }
}