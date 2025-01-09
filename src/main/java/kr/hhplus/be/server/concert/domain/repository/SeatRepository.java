package kr.hhplus.be.server.concert.domain.repository;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.entity.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> findByScheduleIdAndState(Long scheduleId, SeatState state);

    Optional<Seat> findByIdAndState(Long seatId, SeatState state);

    Seat save(Seat seat);
}
