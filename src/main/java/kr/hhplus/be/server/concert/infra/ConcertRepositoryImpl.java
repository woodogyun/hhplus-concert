package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.repository.ConcertRepository;
import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertScheduleJPARepository concertScheduleJPARepository;
    private final SeatJPARepository seatJPARepository;

    @Override
    public List<ConcertSchedule> findAvailableConcertSchedules(long concertId, LocalDateTime currentDate) {
        return concertScheduleJPARepository.findAvailableConcertSchedules(concertId, currentDate);
    }

    @Override
    public List<Seat> findByScheduleIdAndState(Long scheduleId, SeatState state) {
        return seatJPARepository.findByScheduleIdAndState(scheduleId, state);
    }

    @Override
    public Optional<Seat> findByIdAndState(Long seatId, SeatState state) {
        return seatJPARepository.findByIdAndState(seatId, state);
    }

    @Override
    public Seat save(Seat seat) {
        return seatJPARepository.save(seat);
    }

}
