package com.example.kioskhelper.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "chat",indexes =
        {
                @Index(name="idx_chat_session_id",columnList = "sessionId"),
                @Index(name="idx_chat_created_at",columnList = "createdAt"),
                @Index(name="idx_chat_user_id",columnList = "user_id")})
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(length = 2000)
    private String response;

    private LocalDateTime createdAt;
    private String sessionId;
    @Column(columnDefinition = "boolean default false")
    private boolean expired;


    @Builder
    public Chat(User user, String message, String response, LocalDateTime createdAt, String sessionId, boolean expired) {
        this.user = user;
        this.message = message;
        this.response = response;
        this.createdAt = createdAt;
        this.sessionId = sessionId;
        this.expired = expired;
    }

    public Chat() {
    }
}
