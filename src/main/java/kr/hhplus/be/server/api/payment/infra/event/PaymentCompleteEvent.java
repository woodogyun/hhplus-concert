package kr.hhplus.be.server.api.payment.infra.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentCompleteEvent {
    private Long userId;
    private Long reservationId;
    public static PaymentCompleteEvent of(Long userId, Long reservationId) {
        return PaymentCompleteEvent.builder()
                .userId(userId)
                .reservationId(reservationId)
                .build();
    }
}