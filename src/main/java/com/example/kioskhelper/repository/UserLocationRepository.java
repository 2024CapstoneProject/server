package com.example.kioskhelper.repository;

import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.domain.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    // 특정 사용자의 최신 위치 정보 조회
    UserLocation findFirstByUserIdOrderByTimestampDesc(User userId);
}
