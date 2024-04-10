package com.example.kioskhelper.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User guardian;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User ward;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자, 게터, 세터 생략

    public enum Status {
        PENDING, ACCEPTED, DECLINED
    }

    @Builder
    public Protector(User guardian, User ward, Status status) {
        this.guardian = guardian;
        this.ward = ward;
        this.status = status;
    }
}
