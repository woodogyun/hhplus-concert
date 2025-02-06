package kr.hhplus.be.server.api.queue.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.TokenException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "queue")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키 및 순번 파악

    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId; // 유저 아이디

    @Column(nullable = false, unique = true, name = "uuid")
    private String uuid; // UUID

    @Column(name = "expired_at")
    private LocalDateTime expiredAt; // 만료 시간

    @Column(nullable = false, name = "isQueuePassed")
    private boolean isQueuePassed; // 상태

    private Queue(long userId) {
        this.userId = userId;
        this.isQueuePassed = false;
        this.uuid = UUID.randomUUID().toString();
    }

    public static Queue create(long userId) {
        if (userId <= 0) {
            throw new TokenException(ErrorCode.INVALID_USER);
        }
        return new Queue(userId);
    }

    public void passQueue(LocalDateTime expiredAt) {
        this.isQueuePassed = true;
        this.expiredAt = expiredAt;
    }

}