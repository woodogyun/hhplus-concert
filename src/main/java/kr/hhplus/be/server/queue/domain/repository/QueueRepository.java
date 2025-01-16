package kr.hhplus.be.server.queue.domain.repository;

import kr.hhplus.be.server.common.QueueState;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    void save(Queue queue);
    List<Queue> saveAll(List<Queue> queues);
    void deleteAll(List<Queue> queues);
    Optional<Queue> findByScheduleIdAndUserId(Long userId, Long scheduleId);
    List<Queue> findExpiredTokens();
    int countByStatus(QueueState status);
    List<Queue> findTopNByInactive(Pageable pageable);
    Long countByStatusAndIdLessThan(QueueState status, Long id);
    Optional<Queue> findByUuidAndScheduleId(String uuid, Long scheduleId);
    Optional<Queue> findByUuid(String uuid);
    boolean existsByUuid(String uuid);

    void deleteByUserId(long userId);
}
