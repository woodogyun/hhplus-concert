package kr.hhplus.be.server.reservation.application.facade;

import kr.hhplus.be.server.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.concert.domain.service.SeatService;
import kr.hhplus.be.server.config.PolicyProperties;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import kr.hhplus.be.server.reservation.application.dto.ReservationResponse;
import kr.hhplus.be.server.reservation.domain.entity.Reservation;
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

    @Transactional
    public int processExpiredReservations() {
        List<Reservation> expiredReservations = reservationService.findExpiredReservations();

        if (expiredReservations.isEmpty()) {
            return 0;
        }

        int processedCount = 0;

        for (Reservation reservation : expiredReservations) {
            // 해당 좌석을 가능한 상태로 변경
            seatService.makeSeatAvailable(reservation.getSeatId());

            // 예약 상태를 만료 변경
            reservationService.expireReservation(reservation);
            processedCount++;
        }

        return processedCount; // 처리된 예약 수 반환
    }

}
