package kr.hhplus.be.server.api.payment.application.facade;

import kr.hhplus.be.server.api.payment.application.dto.PaymentResponse;
import kr.hhplus.be.server.api.payment.application.dto.PointResponse;
import kr.hhplus.be.server.api.payment.domain.dto.PointResult;
import kr.hhplus.be.server.api.payment.domain.service.PaymentService;
import kr.hhplus.be.server.api.payment.domain.service.PointService;
import kr.hhplus.be.server.api.queue.domain.service.QueueService;
import kr.hhplus.be.server.api.reservation.domain.service.ReservationService;
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
    public PaymentResponse processPayment(long seatId, long userId, long amount) {
        // 1. 유저 포인트 결제
        pointService.pay(userId, amount);
        // 2. Reservation 상태 변경
        long reservationId = reservationService.updateState(seatId);
        // 3. Payment 추가
        paymentService.add(userId, reservationId, amount);
        // 3. Payment 추가
        paymentService.add(userId, reservationId, amount);
        // 4. token 제거
        queueService.delete(userId);
        return new PaymentResponse(true);
    }

    public PointResponse getPoint(Long userId) {
        PointResult point = pointService.getPoint(userId);
        return new PointResponse(point.getPoint());
    }

    @Transactional
    public PointResponse chargePoint(Long userId, int value) {
        Long point = pointService.chargePoint(userId, value);
        return new PointResponse(point);
    }
}
