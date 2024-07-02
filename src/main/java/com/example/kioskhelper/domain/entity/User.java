package com.example.kioskhelper.domain.entity;
import com.example.kioskhelper.domain.etc.Role;
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

    private String uid; // uid
    private String email; // 사용자 이메일
    @Enumerated(EnumType.STRING)
    private Role role;

    //test용 User 생성자
    public User(){
        this.uid = "test_UID";
        this.email = "test@gmail.com";
    }

    @Builder
    public User(Long id,String uid, String email) {
        this.id = id;
        this.uid =uid;
        this.email = email;
        this.role = Role.USER;
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.role = Role.USER;
    }
}