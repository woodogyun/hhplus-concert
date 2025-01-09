package kr.hhplus.be.server.payment.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.payment.application.dto.PaymentResponse;
import kr.hhplus.be.server.payment.application.dto.ReservationResponse;
import kr.hhplus.be.server.payment.application.facade.PaymentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제 API", description = "결제 API")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(summary = "좌석 클릭 시 좌석 상태 변경")
    @PostMapping("/{seat-id}/reserve")
    public ReservationResponse reserveSeat(@PathVariable(value = "seat-id") long seatId,
                                           @RequestHeader("x-token") String uuid) {
        // TODO : 하드코딩 된 유저 아이디 추후에 수정
        long userId = 1L;
        return paymentFacade.reserveSeat(seatId, uuid, userId);
    }

    @Operation(summary = "좌석 결제")
    @PostMapping("/{seat-id}/reserve")
    public PaymentResponse processPayment(@PathVariable(value = "seat-id") String seatId) {
//        return new PaymentResponse(true);
        paymentFacade.processPayment(seatId);
        return null;
    }

}
