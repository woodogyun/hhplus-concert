package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScheduleIdAndState(Long scheduleId,SeatState state);

    Optional<Seat> findByIdAndState(Long id, SeatState state);
}
