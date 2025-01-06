package kr.hhplus.be.server.queue.infra;

import kr.hhplus.be.server.queue.domain.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueueJPARepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByScheduleIdAndUserId(Long userId, Long scheduleId);

}
