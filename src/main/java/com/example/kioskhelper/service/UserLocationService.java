package com.example.kioskhelper.service;

import com.example.kioskhelper.domain.entity.Protector;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.domain.entity.UserLocation;

import com.example.kioskhelper.repository.ProtectorRepository;
import com.example.kioskhelper.repository.UserLocationRepository;
import com.example.kioskhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLocationService {


    private final UserLocationRepository userLocationRepository;
    private final UserRepository userRepository;

    private final ProtectorRepository protectorRepository;



    public void saveUserLocation(UserLocation location) {
        userLocationRepository.save(location);
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
