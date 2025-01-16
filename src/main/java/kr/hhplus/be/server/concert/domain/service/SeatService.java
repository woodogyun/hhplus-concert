package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;

    // scheduleId를 받아 좌석 정보 조회
    public List<SeatResult> getSeats(long scheduleId, SeatState seatStatus) {
        List<Seat> seats = seatRepository.findByScheduleIdAndState(scheduleId, seatStatus);
        return seats.stream()
                .map(SeatResult::fromEntity) // fromEntity 메소드 사용
                .toList();
    }

    // 좌석 선택 시 임시 배정으로 상태 변경 및 만료기간 설정
    @Transactional
    public SeatResult reserveSeat(long seatId) {
        Optional<Seat> opt = seatRepository.findByIdAndState(seatId, SeatState.AVAILABLE);
        Seat seat = opt.orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setState(SeatState.UNAVAILABLE);
        Seat savedSeat = seatRepository.save(seat);
        return SeatResult.fromEntity(savedSeat); // fromEntity 메소드 사용
    }
}
