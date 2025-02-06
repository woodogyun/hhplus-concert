package kr.hhplus.be.server.api.queue.domain.service;

import kr.hhplus.be.server.api.queue.application.dto.TokenResponse;
import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.api.queue.infra.QueueRepositoryImpl;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.TokenException;
import kr.hhplus.be.server.provider.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepositoryImpl queueRepositoryImpl;
    private final TimeProvider timeProvider;

    public TokenResponse generateToken(Long userId) {
        Queue queue = queueRepositoryImpl.save(Queue.create(userId));
        return TokenResponse.from(queue);
    }

    public TokenResponse getQueueStatus(String uuid) {
        Queue queue = queueRepositoryImpl.findByUuid(uuid)
                .orElseThrow(() -> new TokenException(ErrorCode.NOT_FOUND_TOKEN));

        // 진행 가능한 상태인 경우
        if (queue.isQueuePassed()) {
            return TokenResponse.builder()
                    .canEnter(true)
                    .build();
        }

        // 대기 중인 인원 수
        Long waitingCount = queueRepositoryImpl.countByIsQueuePassedAndIdLessThan(false, queue.getId());
        return TokenResponse.builder()
                .waitingCount(waitingCount)
                .canEnter(false)
                .build();
    }

    public int countActiveQueues() {
        return queueRepositoryImpl.countByIsQueuePassed(true);
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
    public int updateToken(List<Queue> queuesToUpdate, int expirationSeconds) {
        if (queuesToUpdate.isEmpty()) {
            return 0; // 업데이트할 큐가 없으면 0 반환
        }

        // 각 큐의 상태와 만료 시간을 업데이트
        queuesToUpdate.forEach(queue -> {
            queue.passQueue(timeProvider.now().plusSeconds(expirationSeconds));
        });

        // 변경 사항을 저장
        List<Queue> list = queueRepositoryImpl.saveAll(queuesToUpdate);
        return list.size();
    }

    public Queue getToken(String uuid) {
        Optional<Queue> opt = queueRepositoryImpl.findByUuid(uuid);
        return opt.orElseThrow(() -> new TokenException(ErrorCode.NOT_FOUND_TOKEN));
    }

    @Transactional
    public void delete(long userId) {
        queueRepositoryImpl.deleteByUserId(userId);
    }

}
