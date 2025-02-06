package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.SeatException;
import kr.hhplus.be.server.api.concert.domain.entity.SeatState;
import kr.hhplus.be.server.api.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.api.concert.domain.entity.Seat;
import kr.hhplus.be.server.api.concert.domain.repository.SeatRepository;
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
        Seat seat = opt.orElseThrow(() -> new SeatException(ErrorCode.NOT_FOUNT_SEAT));
        seat.reserve();
        Seat savedSeat = seatRepository.save(seat);
        return SeatResult.fromEntity(savedSeat); // fromEntity 메소드 사용
    }
    
    // 예약 만료 시 좌석 상태 예약 가능하도록 변경
    public void makeSeatAvailable(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new SeatException(ErrorCode.SEAT_NOT_UNAVAILABLE));
        seat.makeAvailable();
    }
}
