package kr.hhplus.be.server.queue.infra;

import kr.hhplus.be.server.queue.domain.Queue;
import kr.hhplus.be.server.queue.domain.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJPARepository queueJPARepository;

    @Override
    public void save(final Queue queue) {
        queueJPARepository.save(queue);
    }

    @Override
    public Optional<Queue> findByScheduleIdAndUserId(Long userId, Long scheduleId) {
        return queueJPARepository.findByScheduleIdAndUserId(userId, scheduleId);
    }
    @Override
    public Long countByStatusAndIdLessThan(String status, Long id) {
        return queueJPARepository.countByStatusAndIdLessThan(status, id);
    }
    @Override
    public Optional<Queue> findByUuidAndScheduleId(String uuid, Long scheduleId) {
        return queueJPARepository.findByUuidAndScheduleId(uuid, scheduleId);
    }
}
