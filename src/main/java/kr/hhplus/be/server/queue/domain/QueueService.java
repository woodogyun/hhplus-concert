package kr.hhplus.be.server.queue.domain;

import kr.hhplus.be.server.common.QueueStatus;
import kr.hhplus.be.server.queue.dto.TokenResponse;
import kr.hhplus.be.server.queue.infra.QueueRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueService {
    private static final long EXPIRATION_MINUTES = 5; // 만료 시간 정책을 위한 상수 정의
    private final QueueRepositoryImpl queueRepositoryImpl;

    public Queue generateQueue(Long scheduleId, Long userId) {
        // 기존 토큰 조회 후 제거
        String uuid = UUID.randomUUID().toString(); // UUID 생성
        QueueStatus status = QueueStatus.INACTIVE; // 초기 상태를 INACTIVE 으로 설정

        Optional<Queue> opt = queueRepositoryImpl.findByScheduleIdAndUserId(userId, scheduleId);
        Queue queue = opt.orElseGet(() ->
                Queue.builder()
                        .userId(userId)
                        .uuid(uuid)
                        .scheduleId(scheduleId)
                        .status(status.name())
                        .build()
        );
        // Queue 객체 생성 및 저장
        queueRepositoryImpl.save(queue);
        return queue;
    }

    public TokenResponse getQueueStatus(String uuid, Long scheduleId) {
        return null;
    }


    // ACTIVE 상태의 대기열 수를 카운팅하는 메서드
    public int countActiveQueues() {
        return 0;
    }

    // 대기열에 만료된 토큰 제거하는 메서드
    @Transactional
    public long deleteToken() {
        return 0;
    }

    public List<Queue> findTopNByInactive(long count) {
        return null;
    }

    // 최신 순번을 변경하는 메서드
    @Transactional
    public int updateToken(List<Queue> queuesToUpdate) {
        return 0;
    }
}
