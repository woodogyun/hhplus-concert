package kr.hhplus.be.server.concert.application.facade;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.application.dto.response.ConcertDateResponse;
import kr.hhplus.be.server.concert.application.dto.response.SeatResponse;
import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ReservationService reservationService;
    private final ConcertScheduleService concertScheduleService;
    private final SeatService seatService;
    private final QueueService queueService;
    private final PolicyProperties policyProperties;

    public List<ConcertDateResponse> availableSeats(long concertId, LocalDateTime now) {
        List<ConcertSchedule> scheduleList = concertScheduleService.availableSeats(concertId, now);
        return scheduleList.stream()
                .map(schedule -> new ConcertDateResponse(
                        schedule.getId(), // scheduleId
                        schedule.getPerformanceDateAt() // performanceDateAt
                )).toList();
    }

    public List<SeatResponse> getSeats(long scheduleId, SeatState seatState) {
        List<Seat> list = seatService.getSeats(scheduleId, seatState);
        return list.stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getState().name()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse reserveSeat(long seatId, String uuid, Long userId) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(policyProperties.getSeatExpiredMinutes());

        Seat seat = seatService.reserveSeat(seatId);
        Queue queue = queueService.getToken(uuid);
        queueService.updateToken(List.of(queue), expiresAt);
        reservationService.setReserve(seat.getId(), seat.getPrice(), userId, expiresAt);
        return new ReservationResponse(false);
    }

}
