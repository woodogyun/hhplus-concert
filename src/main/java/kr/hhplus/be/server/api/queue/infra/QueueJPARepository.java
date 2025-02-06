package kr.hhplus.be.server.api.queue.infra;

import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueJPARepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByUserId(Long userId);

    @Query("SELECT q FROM queue q WHERE q.expiredAt < CURRENT_TIMESTAMP") // 만료된 토큰 조회
    List<Queue> findExpiredTokens();

    @Query("SELECT COUNT(q) FROM queue q WHERE q.isQueuePassed = :isQueuePassed")
    int countByStatus(@Param("isQueuePassed") boolean isQueuePassed);

    @Query(value = "SELECT q FROM queue q WHERE q.isQueuePassed = false ORDER BY q.id ASC")
    List<Queue> findTopNByInactive(Pageable pageable);

    @Query("SELECT COUNT(q) FROM queue q WHERE q.isQueuePassed = :isQueuePassed AND q.id < :id")
    Long countByStatusAndIdLessThan(@Param("isQueuePassed") boolean isQueuePassed, @Param("id") Long id);

    Optional<Queue> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    void deleteByUserId(long userId);

}
