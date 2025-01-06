package kr.hhplus.be.server.queue.infra;

import kr.hhplus.be.server.queue.domain.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueueJPARepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByScheduleIdAndUserId(Long userId, Long scheduleId);

    @Query("SELECT COUNT(q) FROM queue q WHERE q.status = :status AND q.id < :id")
    Long countByStatusAndIdLessThan(@Param("status") String status, @Param("id") Long id);

    Optional<Queue> findByUuidAndScheduleId(String uuid, Long scheduleId);

}
