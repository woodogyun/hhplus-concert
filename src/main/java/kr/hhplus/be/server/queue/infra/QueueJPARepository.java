package kr.hhplus.be.server.queue.infra;

import kr.hhplus.be.server.queue.domain.entity.QueueState;
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

    Optional<Queue> findByConcertIdAndUserId(Long userId, Long concertId);

    @Query("SELECT q FROM queue q WHERE q.expiresAt < CURRENT_TIMESTAMP") // 만료된 토큰 조회
    List<Queue> findExpiredTokens();

    @Query("SELECT COUNT(q) FROM queue q WHERE q.state = :status")
    int countByStatus(@Param("status") QueueState status);

    @Query(value = "SELECT q FROM queue q WHERE q.state = 'INACTIVE' ORDER BY q.id ASC")
    List<Queue> findTopNByInactive(Pageable pageable);

    @Query("SELECT COUNT(q) FROM queue q WHERE q.state = :status AND q.id < :id")
    Long countByStatusAndIdLessThan(@Param("status") QueueState status, @Param("id") Long id);


    Optional<Queue> findByUuidAndConcertId(String uuid, Long concertId);

    Optional<Queue> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    void deleteByUserId(long userId);

}
