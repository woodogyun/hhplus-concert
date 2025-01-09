package kr.hhplus.be.server.concert.domain;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.dto.ConcertDateResponse;
import kr.hhplus.be.server.concert.dto.SeatResponse;
import kr.hhplus.be.server.concert.infra.ConcertRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepositoryImpl concertRepository;

    //1. concertId 를 받아 예약 가능한 날짜 조회
    public List<ConcertDateResponse> availableSeats(long concertId, LocalDateTime currentDate) {
        List<ConcertSchedule> scheduleList = concertRepository.findAvailableConcertSchedules(concertId, currentDate);
        return scheduleList.stream()
                .map(schedule -> new ConcertDateResponse(
                        schedule.getId(), // scheduleId
                        schedule.getPerformanceDateAt() // performanceDateAt
                )).toList();
    }

    //2. scheduleId를 받아 좌석 정보 조회
    public List<SeatResponse> getSeats(long scheduleId, SeatState seatStatus) {
        List<Seat> list = concertRepository.findByScheduleIdAndState(scheduleId, seatStatus);
        return list.stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getState().name())) // SeatResponse의 생성자에 맞게 수정
                .collect(Collectors.toList());

    }

    //3. 좌석 선택 시 임시 배정으로 상태 변경 및 만료기간 설정
    @Transactional
    public Seat reserveSeat(long seatId) {
        Optional<Seat> opt = concertRepository.findByIdAndState(seatId, SeatState.AVAILABLE);
        Seat seat = opt.orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setState(SeatState.UNAVAILABLE);
        return concertRepository.save(seat);
    }

}
