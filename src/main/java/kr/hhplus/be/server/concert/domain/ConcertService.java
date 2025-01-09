package kr.hhplus.be.server.concert.domain;

import kr.hhplus.be.server.concert.dto.ConcertDateResponse;
import kr.hhplus.be.server.concert.infra.ConcertRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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



}
