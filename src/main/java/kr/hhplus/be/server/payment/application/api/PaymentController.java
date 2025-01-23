package kr.hhplus.be.server.payment.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.annotation.ApiLog;
import kr.hhplus.be.server.payment.application.dto.PaymentRequest;
import kr.hhplus.be.server.payment.application.dto.PaymentResponse;
import kr.hhplus.be.server.payment.application.dto.PointResponse;
import kr.hhplus.be.server.payment.application.facade.PaymentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제 API", description = "결제 API")
@ApiLog
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(summary = "포인트 조회")
    @GetMapping("/{user-id}/point")
    public ResponseEntity<PointResponse> getPoint(@PathVariable(value = "user-id") Long userId) {
        return ResponseEntity.ok().body(paymentFacade.getPoint(userId));
    }

    @Operation(summary = "포인트 충전")
    @PostMapping("/{user-id}/point/charge")
    public ResponseEntity<PointResponse> pointCharge(@PathVariable(value = "user-id") Long userId, @RequestBody int value) {
        return ResponseEntity.ok().body(paymentFacade.chargePoint(userId, value));
    }

    //ㅁㄴ앤ㅁㅇ
    @Operation(summary = "좌석 결제")
    @PostMapping("/{seat-id}/reserve")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable(value = "seat-id") long seatId, @RequestBody PaymentRequest request) {
        return ResponseEntity.ok().body(paymentFacade.processPayment(seatId, request.userId(), request.amount()));
    }

}
