package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.payment.domain.entity.Payment;
import kr.hhplus.be.server.payment.infra.PaymentRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepositoryImpl paymentRepository;

    public void add(long userId, long reservationId, long amount) {
        Payment payment = Payment.create(userId, reservationId, amount);
        paymentRepository.save(payment);
    }
}