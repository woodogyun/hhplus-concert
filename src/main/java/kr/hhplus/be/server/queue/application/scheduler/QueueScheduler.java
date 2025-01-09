package kr.hhplus.be.server.queue.application.scheduler;

import kr.hhplus.be.server.config.PolicyProperties;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class QueueScheduler {

    private final PolicyProperties policyProperties; // QueueProperties 주입
    private final QueueService queueService;

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void manageQueue() {
        // 만료된 토큰 제거
        long expiredTokenCount = queueService.deleteToken(); // 만료된 토큰 수를 반환하는 메서드 호출
        log.info("Expired Token Count: {}", expiredTokenCount);

        // ACTIVE 상태의 대기열 수 조회
        int activeQueueCount = queueService.countActiveQueues();
        log.info("Active Queue Count: {}", activeQueueCount);

        // ACTIVE 상태의 대기열 수가 50명 미만인 경우
        if (activeQueueCount < policyProperties.getMaxQueueCapacity()) {
            long toEnter = policyProperties.getMaxQueueCapacity() - activeQueueCount; // 진입할 인원 수 계산
            List<Queue> queuesToUpdate = queueService.findTopNByInactive(toEnter);
            int updateTokenCount = queueService.updateToken(queuesToUpdate, policyProperties.getExpirationMinutes()); // 최신 순번 변경 로직 호출
            log.info("Update Queue Count: {}", updateTokenCount);

        }
    }

}