package kr.hhplus.be.server.payment.application.facade;

import kr.hhplus.be.server.payment.domain.service.PaymentService;
import kr.hhplus.be.server.payment.domain.service.PointService;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import kr.hhplus.be.server.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final QueueService queueService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final ReservationService reservationService;

    @Transactional
    public void processPayment(long seatId, long userId, long amount) {
        // 1. 유저 포인트 결제
        pointService.pay(userId, amount);
        // 2. Reservation 상태 변경
        long reservationId = reservationService.updateState(seatId);
        // 3. Payment 추가
        paymentService.add(userId, reservationId, amount);
        // 4. token 제거
        queueService.delete(userId);
    }
}
