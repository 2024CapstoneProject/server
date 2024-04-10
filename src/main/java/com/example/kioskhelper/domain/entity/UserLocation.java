package com.example.kioskhelper.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_location")
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래 키를 지정
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user; // 사용자 엔티티와의 관계 설정

    private Double latitude; // 위도
    private Double longitude; // 경도
    private LocalDateTime timestamp; // 위치 정보 저장 시각


}