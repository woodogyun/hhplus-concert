package kr.hhplus.be.server.concert.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.concert.domain.Seat;
import kr.hhplus.be.server.concert.application.dto.ConcertDateResponse;
import kr.hhplus.be.server.concert.application.dto.SeatResponse;
import kr.hhplus.be.server.config.PolicyProperties;
import kr.hhplus.be.server.payment.domain.ReservationService;
import kr.hhplus.be.server.payment.dto.ReservationResponse;
import kr.hhplus.be.server.queue.domain.Queue;
import kr.hhplus.be.server.queue.domain.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/concert")
@RequiredArgsConstructor
@Tag(name = "콘서트 API", description = "콘서트 API")
public class ConcertController {

    private final PolicyProperties policyProperties;
    private final ConcertService concertService;
    private final QueueService queueService;
    private final ReservationService reservationService;

    @Operation(summary = "콘서트 예약 가능 날짜")
    @GetMapping("/{concert-id}")
    public List<ConcertDateResponse> getAvailableDates(@PathVariable(value = "concert-id") long concertId) {
        return concertService.availableSeats(concertId, LocalDateTime.now());
    }
    
    @Operation(summary = "좌석 조회")
    @GetMapping("/{schedule-id}/seat")
    public List<SeatResponse> getSeats(@PathVariable(value = "schedule-id") long scheduleId) {
        SeatState seatState = SeatState.AVAILABLE;
        return concertService.getSeats(scheduleId, seatState);
    }

    @Operation(summary = "좌석 클릭 시 좌석 상태 변경")
    @PostMapping("/{seat-id}/reserve")
    public ReservationResponse reserveSeat(@PathVariable(value = "seat-id") long seatId,
                                           @RequestHeader("x-token") String uuid) {
        // TODO : 하드코딩 된 유저 아이디 추후에 수정
        long userId = 1L;
        
        Seat seat = concertService.reserveSeat(seatId);
        Queue queue = queueService.getToken(uuid);
        queueService.updateToken(List.of(queue), policyProperties.getSeatExpiredMinutes());
        reservationService.setReserve(seat.getId(), seat.getPrice(), userId);
        return new ReservationResponse(false);
    }

}
