package kr.hhplus.be.server.concert.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.application.dto.response.ConcertDateResponse;
import kr.hhplus.be.server.concert.application.dto.response.SeatResponse;
import kr.hhplus.be.server.concert.application.facade.ConcertFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/concert")
@RequiredArgsConstructor
@Tag(name = "콘서트 API", description = "콘서트 API")
public class ConcertController {

    private final ConcertFacade concertFacade;

    @Operation(summary = "콘서트 예약 가능 날짜")
    @GetMapping("/{concert-id}")
    public List<ConcertDateResponse> getAvailableDates(@PathVariable(value = "concert-id") long concertId) {
        return concertFacade.availableSeats(concertId, LocalDateTime.now());
    }
    
    @Operation(summary = "좌석 조회")
    @GetMapping("/{schedule-id}/seat")
    public List<SeatResponse> getSeats(@PathVariable(value = "schedule-id") long scheduleId) {
        SeatState seatState = SeatState.AVAILABLE;
        return concertFacade.getSeats(scheduleId, seatState);
    }

}