package kr.hhplus.be.server.queue.infra;

import kr.hhplus.be.server.queue.domain.entity.Queue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueJPARepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByScheduleIdAndUserId(Long userId, Long scheduleId);

    @Query("SELECT q FROM queue q WHERE q.expiresAt < CURRENT_TIMESTAMP") // 만료된 토큰 조회
    List<Queue> findExpiredTokens();

    @Query("SELECT COUNT(q) FROM queue q WHERE q.status = :status")
    int countByStatus(@Param("status") String status);

    @Query(value = "SELECT q FROM queue q WHERE q.status = 'INACTIVE' ORDER BY q.id ASC")
    List<Queue> findTopNByInactive(Pageable pageable);

    @Query("SELECT COUNT(q) FROM queue q WHERE q.status = :status AND q.id < :id")
    Long countByStatusAndIdLessThan(@Param("status") String status, @Param("id") Long id);

    Optional<Queue> findByUuidAndScheduleId(String uuid, Long scheduleId);

    Optional<Queue> findByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
