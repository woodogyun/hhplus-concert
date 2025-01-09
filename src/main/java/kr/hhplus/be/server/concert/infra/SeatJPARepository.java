package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScheduleIdAndState(Long scheduleId,SeatState state);

}
