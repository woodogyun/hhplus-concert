package kr.hhplus.be.server.concert.domain;

import kr.hhplus.be.server.common.SeatState;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository {

    List<ConcertSchedule> findAvailableConcertSchedules(long concertId, LocalDateTime currentDate);

    List<Seat> findByScheduleIdAndState(Long scheduleId,SeatState state);

}
