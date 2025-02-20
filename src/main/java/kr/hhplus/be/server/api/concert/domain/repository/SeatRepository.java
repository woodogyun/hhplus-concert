package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.entity.SeatState;
import kr.hhplus.be.server.api.concert.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScheduleIdAndState(Long scheduleId,SeatState state);

    Optional<Seat> findByIdAndState(Long id, SeatState state);
}
