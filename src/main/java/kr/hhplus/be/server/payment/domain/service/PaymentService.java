package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.payment.domain.entity.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void add(long userId, long reservationId, int amount) {
        Payment payment = Payment.builder()
                .userId(userId)
                .amount(amount)
                .reservationId(reservationId)
                .build();

        paymentRepository.save(payment);
    }
}
