package kr.hhplus.be.server.api.queue.infra;

import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.api.queue.domain.repository.QueueRepository;
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
    public Queue save(final Queue queue) {
        return queueJPARepository.save(queue);
    }

    @Override
    public Optional<Queue> findByUserId(Long userId) {
        return queueJPARepository.findByUserId(userId);
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
    public Long countByIsQueuePassedAndIdLessThan(boolean isQueuePassed, Long id) {
        return queueJPARepository.countByStatusAndIdLessThan(isQueuePassed, id);
    }

    @Override
    public int countByIsQueuePassed(boolean isQueuePassed) {
        return (int) queueJPARepository.countByStatus(isQueuePassed);
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
    public void deleteByUserId(long userId) {
        queueJPARepository.deleteByUserId(userId);
    }

    @Override
    public void deleteAll(List<Queue> queues) {
        queueJPARepository.deleteAll(queues);
    }

    @Override
    public List<Queue> saveAll(List<Queue> queues) {
        return queueJPARepository.saveAll(queues);
    }

    public void deleteAll() {
        queueJPARepository.deleteAll();
    }

}
