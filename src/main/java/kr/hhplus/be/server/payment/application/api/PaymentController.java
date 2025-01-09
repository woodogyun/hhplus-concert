package kr.hhplus.be.server.payment.application.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.payment.application.dto.PaymentResponse;
import kr.hhplus.be.server.payment.application.dto.ReservationResponse;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "결제 API", description = "결제 API")
public class PaymentController {

    @Operation(summary = "좌석 클릭 시 좌석 상태 변경")
    @PostMapping("/{shedule-id}/reserve")
    public ReservationResponse reserveSeat(@PathVariable(value = "shedule-id") String sheduleId) {
        return new ReservationResponse(false);
    }
    
    @Operation(summary = "좌석 결제")
    @PostMapping("/{seat-id}/reserve")
    public PaymentResponse processPayment(@PathVariable(value = "seat-id") String seatId) {
        return new PaymentResponse(true);
    }

}
