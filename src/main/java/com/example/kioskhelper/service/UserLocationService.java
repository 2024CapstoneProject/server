package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.dto.LocationResponse;
import com.example.kioskhelper.domain.entity.Protector;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.domain.entity.UserLocation;

import com.example.kioskhelper.repository.ProtectorRepository;
import com.example.kioskhelper.repository.UserLocationRepository;
import com.example.kioskhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserLocationService {


    private final UserLocationRepository userLocationRepository;
    private final UserRepository userRepository;

    private final ProtectorRepository protectorRepository;

    private UserService userService;



    public void saveUserLocation(LocationResponse location) {



        User user = userRepository.findById(location.getUserId()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        UserLocation locations = new UserLocation();
        locations.setUser(user);
        locations.setLatitude(location.getLatitude());
        locations.setLongitude(location.getLongitude());
        locations.setTimestamp(LocalDateTime.now()); // 현재 시각 설정

        userLocationRepository.save(locations);


    }

    public UserLocation getUserLatestLocation(User protector,Long userId) {
        //해당 유저가 있는지 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        //해당 유저의 보호자 관계인지 확인

        Protector protect=protectorRepository.findByGuardianAndWardAndStatus(protector, user, Protector.Status.ACCEPTED);
        if(protect==null){
            throw new IllegalArgumentException("해당 사용자의 보호자가 아닙니다.");
        }
        return userLocationRepository.findFirstByUserIdOrderByTimestampDesc(user);
    }
}
