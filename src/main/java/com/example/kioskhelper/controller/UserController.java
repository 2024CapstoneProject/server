package com.example.kioskhelper.controller;

import com.example.kioskhelper.domain.entity.Protector;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.repository.ProtectorRepository;
import com.example.kioskhelper.repository.UserRepository;
import com.example.kioskhelper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final ProtectorRepository protectorRepository;

    private final UserService userService;

    @PostMapping("/test")
    public void saveUser() {
        // 사용자 정보를 저장하는 로직을 구현
        User gaurdian1= User.builder()
                .uid("gaurdian1")
                .email("test@gmail.com")
                .build();
        userService.saveUser(gaurdian1);

        User ward1= User.builder()
                .uid("ward1")
                .email("test@gmail.com")
                .build();
        userService.saveUser(ward1);

        Protector protector1=Protector.builder()
                .guardian(gaurdian1)
                .ward(ward1)
                .status(Protector.Status.ACCEPTED)
                .build();

        protectorRepository.save(protector1);


    }

    @DeleteMapping("/test")
    public void deleteUser() {
        // 사용자 정보를 삭제하는 로직을 구현
        userRepository.deleteAll();
        protectorRepository.deleteAll();
    }


}
