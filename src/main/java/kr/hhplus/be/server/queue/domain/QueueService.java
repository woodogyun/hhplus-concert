package kr.hhplus.be.server.queue.domain;

import kr.hhplus.be.server.common.QueueStatus;
import kr.hhplus.be.server.queue.dto.TokenResponse;
import kr.hhplus.be.server.queue.infra.QueueRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        // UUID, scheduleId를 통한 대기열 상태 조회
        Queue queue = queueRepositoryImpl.findByUuidAndScheduleId(uuid, scheduleId)
                .orElseThrow(() -> new RuntimeException("대기열을 찾을 수 없습니다.")); // UUID로 대기열 조회

        // 상태 값이 ACTIVE 인 경우
        if (queue.getStatus().equals("ACTIVE")) {
            return new TokenResponse(true);
        }

        // 대기 중인 인원 수
        Long waitingCount = queueRepositoryImpl.countByStatusAndIdLessThan("INACTIVE", queue.getId());

        // TokenResponse 객체 생성 및 반환
        return new TokenResponse( waitingCount, false);
    }


    // ACTIVE 상태의 대기열 수를 카운팅하는 메서드
    public int countActiveQueues() {
        return queueRepositoryImpl.countByStatus("ACTIVE");
    }

    // 대기열에 만료된 토큰 제거하는 메서드
    @Transactional
    public long deleteToken() {
        List<Queue> expiredTokens = queueRepositoryImpl.findExpiredTokens(); // 만료된 토큰 조회
        long count = expiredTokens.size(); // 제거할 토큰 수 계산

        if(count > 0) {
            queueRepositoryImpl.deleteAll(expiredTokens); // 만료된 토큰 삭제
        }

        return count; // 제거된 토큰 수 반환
    }

    public List<Queue> findTopNByInactive(long count) {
        // 탑 N 개의 "INACTIVE" 상태 큐를 조회
        Pageable pageable = PageRequest.of(0, (int) count); // 페이지 번호는 0부터 시작
        return queueRepositoryImpl.findTopNByInactive(pageable);
    }

    // 최신 순번을 변경하는 메서드
    @Transactional
    public int updateToken(List<Queue> queuesToUpdate) {
        // 현재 시간 + 만료 시간 계산
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);

        // 각 토큰의 상태와 만료 시간을 업데이트
        for (Queue queue : queuesToUpdate) {
            queue.setStatus("ACTIVE");
            queue.setExpiresAt(expiresAt);
        }

        // 변경 사항을 저장
        List<Queue> list = queueRepositoryImpl.saveAll(queuesToUpdate);
        return list.size();
    }
}
