package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.payment.domain.entity.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void testAdd() {
        long userId = 1L;
        long reservationId = 100L;
        long amount = 500L;

        paymentService.add(userId, reservationId, amount);

        Payment expectedPayment = Payment.builder()
                .userId(userId)
                .amount(amount)
                .reservationId(reservationId)
                .build();

        verify(paymentRepository).save(expectedPayment);
    }
}
