package kr.hhplus.be.server.queue.domain;

import java.util.Optional;

public interface QueueRepository {
    void save(Queue queue);
    Optional<Queue> findByScheduleIdAndUserId(Long userId, Long scheduleId);
}
