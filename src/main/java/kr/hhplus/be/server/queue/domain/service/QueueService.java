package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.common.QueueState;
import kr.hhplus.be.server.common.error.InvalidTokenException;
import kr.hhplus.be.server.queue.application.dto.TokenResponse;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.infra.QueueRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepositoryImpl queueRepositoryImpl;

    public Queue generateQueue(Long scheduleId, Long userId) {
        Queue queue = queueRepositoryImpl.findByScheduleIdAndUserId(userId, scheduleId)
                .orElseGet(() -> Queue.create(userId, scheduleId));

        // Queue 객체 저장
        queueRepositoryImpl.save(queue);
        return queue;
    }

    public TokenResponse getQueueStatus(String uuid, Long scheduleId) {
        // UUID, scheduleId를 통한 대기열 상태 조회
        Queue queue = queueRepositoryImpl.findByUuidAndScheduleId(uuid, scheduleId)
                .orElseThrow(() -> new RuntimeException("대기열을 찾을 수 없습니다."));

        // 상태 값이 ACTIVE 인 경우
        if (queue.getState() == QueueState.ACTIVE) {
            return new TokenResponse(true);
        }

        // 대기 중인 인원 수
        Long waitingCount = queueRepositoryImpl.countByStatusAndIdLessThan(QueueState.INACTIVE, queue.getId());

        // TokenResponse 객체 생성 및 반환
        return new TokenResponse(waitingCount, false);
    }

    public int countActiveQueues() {
        return queueRepositoryImpl.countByStatus(QueueState.ACTIVE);
    }

    @Transactional
    public long deleteToken() {
        List<Queue> expiredTokens = queueRepositoryImpl.findExpiredTokens();
        long count = expiredTokens.size();

        if (count > 0) {
            queueRepositoryImpl.deleteAll(expiredTokens);
        }

        return count;
    }

    public List<Queue> findTopNByInactive(long count) {
        Pageable pageable = PageRequest.of(0, (int) count);
        return queueRepositoryImpl.findTopNByInactive(pageable);
    }

    @Transactional
    public int updateToken(List<Queue> queuesToUpdate, LocalDateTime expiresAt) {
        // 각 토큰의 상태와 만료 시간을 업데이트
        for (Queue queue : queuesToUpdate) {
            queue.activate(); // 상태를 ACTIVE로 변경
            queue.setExpiration(expiresAt); // 만료 시간 설정
        }

        // 변경 사항을 저장
        List<Queue> list = queueRepositoryImpl.saveAll(queuesToUpdate);
        return list.size();
    }

    public Queue getToken(String uuid) {
        Optional<Queue> opt = queueRepositoryImpl.findByUuid(uuid);
        return opt.orElseThrow(() -> new InvalidTokenException("Not found token"));
    }

    @Transactional
    public void delete(long userId) {
        queueRepositoryImpl.deleteByUserId(userId);
    }
}
