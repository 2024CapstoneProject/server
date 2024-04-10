package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByName(String name) {
        User user = userRepository.findByName(name);

      return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
