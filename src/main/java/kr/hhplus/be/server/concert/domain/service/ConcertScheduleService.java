package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertScheduleService {

    private final ConcertScheduleRepository concertScheduleRepository;

    // concertId 를 받아 예약 가능한 날짜 조회
    public List<ConcertScheduleResult> availableSeats(long concertId) {
        List<ConcertSchedule> scheduleList = concertScheduleRepository.findAvailableConcertSchedules(concertId, LocalDateTime.now());
        return scheduleList.stream()
                .map(schedule -> ConcertScheduleResult.fromEntity(
                        schedule.getId(), // scheduleId
                        schedule.getPerformanceDateAt() // performanceDateAt
                )).toList();

    }

}
