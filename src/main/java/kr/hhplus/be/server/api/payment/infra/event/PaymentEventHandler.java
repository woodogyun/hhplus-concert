package kr.hhplus.be.server.api.payment.infra.event;

import kr.hhplus.be.server.api.mock.service.MockDataPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor

public class PaymentEventHandler {
    private final MockDataPlatformService mockDataPlatformService;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentCompleteEventHandler(PaymentCompleteEvent event) {
        String message = String.format("사용자(UserId: %d) 결제 성공!", event.getUserId());
        mockDataPlatformService.sendMessage(message);
    }
}