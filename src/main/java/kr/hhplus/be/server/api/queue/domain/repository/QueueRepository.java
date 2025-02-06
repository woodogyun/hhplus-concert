package kr.hhplus.be.server.api.queue.domain.repository;

import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    Queue save(Queue queue);
    List<Queue> saveAll(List<Queue> queues);
    void deleteAll(List<Queue> queues);
    Optional<Queue> findByUserId(Long userId);
    List<Queue> findExpiredTokens();
    int countByIsQueuePassed(boolean isQueuePassed);
    List<Queue> findTopNByInactive(Pageable pageable);
    Long countByIsQueuePassedAndIdLessThan(boolean isQueuePassed, Long id);
    Optional<Queue> findByUuid(String uuid);
    boolean existsByUuid(String uuid);
    void deleteByUserId(long userId);
}
