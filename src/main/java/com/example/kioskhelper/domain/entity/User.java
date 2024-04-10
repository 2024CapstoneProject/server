package com.example.kioskhelper.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 사용자 이름
    private String email; // 사용자 이메일

    //test용 User 생성자
    public User(){
        this.name = "test";
        this.email = "test@gmail.com";
    }

    @Builder
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}