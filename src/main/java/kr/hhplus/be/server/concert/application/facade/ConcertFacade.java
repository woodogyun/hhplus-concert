package kr.hhplus.be.server.concert.application.facade;

import kr.hhplus.be.server.concert.application.dto.response.ConcertDateResponse;
import kr.hhplus.be.server.concert.application.dto.response.SeatResponse;
import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.concert.domain.entity.SeatState;
import kr.hhplus.be.server.concert.domain.service.ConcertScheduleService;
import kr.hhplus.be.server.concert.domain.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertScheduleService concertScheduleService;
    private final SeatService seatService;

    public List<ConcertDateResponse> availableSeats(long concertId) {
        List<ConcertScheduleResult> scheduleList = concertScheduleService.availableSeats(concertId);
        return scheduleList.stream()
                .map(schedule -> new ConcertDateResponse(
                        schedule.getId(), // scheduleId
                        schedule.getPerformanceDateAt() // performanceDateAt
                )).toList();
    }

    public List<SeatResponse> getSeats(long scheduleId, SeatState seatState) {
        List<SeatResult> list = seatService.getSeats(scheduleId, seatState);
        return list.stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getState().name()))
                .collect(Collectors.toList());
    }

}
