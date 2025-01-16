package kr.hhplus.be.server.reservation.application.facade;

import kr.hhplus.be.server.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.concert.domain.service.SeatService;
import kr.hhplus.be.server.config.PolicyProperties;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import kr.hhplus.be.server.reservation.application.dto.ReservationResponse;
import kr.hhplus.be.server.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final SeatService seatService;
    private final ReservationService reservationService;
    private final QueueService queueService;
    private final PolicyProperties policyProperties;

    @Transactional
    public ReservationResponse reserveSeat(long seatId, String uuid, Long userId) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(policyProperties.getSeatExpiredMinutes());

        SeatResult seat = seatService.reserveSeat(seatId);
        Queue queue = queueService.getToken(uuid);
        queueService.updateToken(List.of(queue), expiresAt);
        reservationService.setReserve(seat.getId(), seat.getPrice(), userId, expiresAt);
        return new ReservationResponse(false);
    }

}
