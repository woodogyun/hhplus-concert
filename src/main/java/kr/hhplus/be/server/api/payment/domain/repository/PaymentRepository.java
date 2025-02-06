package kr.hhplus.be.server.api.payment.domain.repository;

import kr.hhplus.be.server.api.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
