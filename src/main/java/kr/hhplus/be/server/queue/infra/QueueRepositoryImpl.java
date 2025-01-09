package kr.hhplus.be.server.queue.infra;

import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<Queue> findExpiredTokens() {
        return queueJPARepository.findExpiredTokens();
    }

    @Override
    public List<Queue> findTopNByInactive(Pageable pageable) {
        return queueJPARepository.findTopNByInactive(pageable);
    }

    @Override
    public Long countByStatusAndIdLessThan(String status, Long id) {
        return queueJPARepository.countByStatusAndIdLessThan(status, id);
    }

    @Override
    public int countByStatus(String status) {
        return (int) queueJPARepository.countByStatus(status);
    }

    @Override
    public Optional<Queue> findByUuidAndScheduleId(String uuid, Long scheduleId) {
        return queueJPARepository.findByUuidAndScheduleId(uuid, scheduleId);
    }

    @Override
    public Optional<Queue> findByUuid(String uuid) {
        return queueJPARepository.findByUuid(uuid);
    }

    @Override
    public boolean existsByUuid(String uuid) {
        return queueJPARepository.existsByUuid(uuid);
    }

    @Override
    public void deleteAll(List<Queue> queues) {
        queueJPARepository.deleteAll(queues);
    }

    @Override
    public List<Queue> saveAll(List<Queue> queues) {
        return queueJPARepository.saveAll(queues);
    }

}
