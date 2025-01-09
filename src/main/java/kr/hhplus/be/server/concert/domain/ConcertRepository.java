package kr.hhplus.be.server.concert.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository {

    List<ConcertSchedule> findAvailableConcertSchedules(long concertId, LocalDateTime currentDate);

}
