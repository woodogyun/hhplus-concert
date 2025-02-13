package kr.hhplus.be.server.api.payment.application.facade;

import kr.hhplus.be.server.api.payment.application.dto.PaymentResponse;
import kr.hhplus.be.server.api.payment.application.dto.PointResponse;
import kr.hhplus.be.server.api.payment.domain.dto.PointResult;
import kr.hhplus.be.server.api.payment.domain.service.PaymentService;
import kr.hhplus.be.server.api.payment.domain.service.PointService;
import kr.hhplus.be.server.api.payment.infra.event.PaymentCompleteEvent;
import kr.hhplus.be.server.api.payment.infra.event.PaymentEventHandler;
import kr.hhplus.be.server.api.reservation.domain.service.ReservationService;
import kr.hhplus.be.server.common.annotation.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final PointService pointService;
    private final ReservationService reservationService;
    private final PaymentEventHandler paymentEventHandler;

    @DistributedLock(key = "'point:' + #userId")
    @Transactional
    public PaymentResponse processPayment(long seatId, long userId, long amount) {
        // 1. 유저 포인트 결제
        pointService.pay(userId, amount);
        // 2. Reservation 상태 변경
        long reservationId = reservationService.updateState(seatId);
        // 3. Payment 추가
        paymentService.add(userId, reservationId, amount);
        // 4. 이벤트 발행
        paymentEventHandler.paymentCompleteEventHandler(PaymentCompleteEvent.of(userId, reservationId));
        return new PaymentResponse(true);
    }

    public PointResponse getPoint(Long userId) {
        PointResult point = pointService.getPoint(userId);
        return new PointResponse(point.getPoint());
    }

    @DistributedLock(key = "'point:' + #userId")
    @Transactional
    public PointResponse chargePoint(Long userId, int value) {
        Long point = pointService.chargePoint(userId, value);
        return new PointResponse(point);
    }
}
