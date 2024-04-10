package com.example.kioskhelper.domain.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_protector_status", columnList = "status"),
        @Index(name = "idx_protector_guardian_ward", columnList = "guardian_id,ward_id")
})
public class Protector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private User guardian;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private User ward;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자, 게터, 세터 생략

    public enum Status {
        PENDING, ACCEPTED, DECLINED
    }
}
