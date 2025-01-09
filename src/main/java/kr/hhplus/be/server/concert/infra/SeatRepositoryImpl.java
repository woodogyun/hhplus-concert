package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.repository.SeatRepository;
import kr.hhplus.be.server.concert.infra.orm.SeatJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJPARepository seatJPARepository;

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
