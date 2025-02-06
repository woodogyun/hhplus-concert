package kr.hhplus.be.server.api.payment.domain.service;

import kr.hhplus.be.server.api.payment.domain.entity.Payment;
import kr.hhplus.be.server.api.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void add(long userId, long reservationId, long amount) {
        Payment payment = Payment.create(userId, reservationId, amount);
        paymentRepository.save(payment);
    }
}