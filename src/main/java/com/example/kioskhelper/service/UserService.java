package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final EncryptionService encryptionService;

    public void saveUser(String uid, String email){
        User user = new User(uid, email);
        userRepository.save(user);
    }

    public User findUserByName(String uid) {
        String encryptedUid = encryptionService.encrypt(uid);
        return userRepository.findByName(encryptedUid);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
