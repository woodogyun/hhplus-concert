package kr.hhplus.be.server.concert.application.facade;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.application.dto.ConcertDateResponse;
import kr.hhplus.be.server.concert.application.dto.SeatResponse;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.service.ConcertScheduleService;
import kr.hhplus.be.server.concert.domain.service.SeatService;
import kr.hhplus.be.server.config.PolicyProperties;
import kr.hhplus.be.server.payment.application.dto.ReservationResponse;
import kr.hhplus.be.server.payment.domain.service.ReservationService;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ReservationService reservationService;
    private final ConcertScheduleService concertScheduleService;
    private final SeatService seatService;
    private final QueueService queueService;
    private final PolicyProperties policyProperties;

    public List<ConcertDateResponse> availableSeats(long concertId, LocalDateTime now) {
        return concertScheduleService.availableSeats(concertId, now);
    }

    public List<SeatResponse> getSeats(long scheduleId, SeatState seatState) {
        return seatService.getSeats(scheduleId, seatState);
    }

    public ReservationResponse reserveSeat(long seatId, String uuid, Long userId) {
        Seat seat = seatService.reserveSeat(seatId);
        Queue queue = queueService.getToken(uuid);
        queueService.updateToken(List.of(queue), policyProperties.getSeatExpiredMinutes());
        reservationService.setReserve(seat.getId(), seat.getPrice(), userId);
        return new ReservationResponse(false);
    }

}
