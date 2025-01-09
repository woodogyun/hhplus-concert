package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.concert.domain.ConcertRepository;
import kr.hhplus.be.server.concert.domain.ConcertSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertScheduleJPARepository concertScheduleJPARepository;

    @Override
    public List<ConcertSchedule> findAvailableConcertSchedules(long concertId, LocalDateTime currentDate) {
        return concertScheduleJPARepository.findAvailableConcertSchedules(concertId, currentDate);
    }


}
