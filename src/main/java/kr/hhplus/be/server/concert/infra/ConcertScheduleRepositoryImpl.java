package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.concert.infra.orm.ConcertScheduleJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJPARepository concertScheduleJPARepository;

    @Override
    public List<ConcertSchedule> findAvailableConcertSchedules(long concertId) {
        return concertScheduleJPARepository.findAvailableConcertSchedules(concertId, LocalDateTime.now());
    }

}
