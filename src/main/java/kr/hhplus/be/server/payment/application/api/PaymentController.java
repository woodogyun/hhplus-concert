package kr.hhplus.be.server.payment.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.payment.application.dto.PaymentRequest;
import kr.hhplus.be.server.payment.application.dto.PaymentResponse;
import kr.hhplus.be.server.payment.application.dto.PointResponse;
import kr.hhplus.be.server.payment.application.facade.PaymentFacade;
import kr.hhplus.be.server.payment.domain.entity.Point;
import kr.hhplus.be.server.payment.domain.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제 API", description = "결제 API")
public class PaymentController {

    private final PaymentFacade paymentFacade;
    private final PointService pointService;

    @Operation(summary = "포인트 조회")
    @GetMapping("/{user-id}/point")
    public PointResponse getPoint(@PathVariable(value = "user-id") Long userId) {
        Point point = pointService.getPoint(userId);
        return new PointResponse(point.getValue());
    }

    @Operation(summary = "포인트 충전")
    @PostMapping("/{user-id}/point/charge")
    public PointResponse pointCharge(@PathVariable(value = "user-id") Long userId, @RequestBody int value) {
        Long amount = pointService.chargePoint(userId, value);
        return new PointResponse(amount);
    }

    @Operation(summary = "좌석 결제")
    @PostMapping("/{seat-id}/reserve")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable(value = "seat-id") long seatId, @RequestBody PaymentRequest request) {
        // TODO : 하드코딩 된 유저 아이디 추후에 수정
        long userId = 1L;
        paymentFacade.processPayment(seatId, request.reservationId(), request.amount());
        return null;
    }

}
