package kr.hhplus.be.server.queue.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.QueueState;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "queue")
@Builder
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키 및 순번 파악

    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId; // 유저 아이디

    @Column(nullable = false, unique = true, name = "uuid")
    private String uuid; // UUID

    @Column(nullable = false, name = "schedule_id")
    private Long scheduleId; // 스케줄 ID

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // 만료 시간

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "state")
    private QueueState state; // 상태

    // 정적 팩토리 메서드
    public static Queue create(Long userId, Long scheduleId) {
        String uuid = UUID.randomUUID().toString(); // UUID 생성
        Queue queue = new Queue();
        queue.setUserId(userId);
        queue.setUuid(uuid);
        queue.setScheduleId(scheduleId);
        queue.setState(QueueState.INACTIVE); // 초기 상태를 INACTIVE로 설정
        return queue;
    }

    // 상태를 변경하는 메서드
    public void activate() {
        this.state = QueueState.ACTIVE;
    }

    public void deactivate() {
        this.state = QueueState.INACTIVE;
    }

    // 만료 시간을 설정하는 메서드
    public void setExpiration(LocalDateTime expirationTime) {
        this.expiresAt = expirationTime;
    }

    // 대기 중인 인원 수 계산 메서드
    public static Long countWaiting(List<Queue> queues, Long currentId) {
        return queues.stream()
                .filter(queue -> queue.getState() == QueueState.INACTIVE && queue.getId() < currentId)
                .count();
    }

}