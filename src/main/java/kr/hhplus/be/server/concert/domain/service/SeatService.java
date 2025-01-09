package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.application.dto.SeatResponse;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;

    // scheduleId를 받아 좌석 정보 조회
    public List<SeatResponse> getSeats(long scheduleId, SeatState seatStatus) {
        List<Seat> list = seatRepository.findByScheduleIdAndState(scheduleId, seatStatus);
        return list.stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getState().name())) // SeatResponse의 생성자에 맞게 수정
                .collect(Collectors.toList());

    }

    // 좌석 선택 시 임시 배정으로 상태 변경 및 만료기간 설정
    @Transactional
    public Seat reserveSeat(long seatId) {
        Optional<Seat> opt = seatRepository.findByIdAndState(seatId, SeatState.AVAILABLE);
        Seat seat = opt.orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setState(SeatState.UNAVAILABLE);
        return seatRepository.save(seat);
    }
}
