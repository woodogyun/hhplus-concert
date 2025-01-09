package kr.hhplus.be.server.payment.domain.repository;

import kr.hhplus.be.server.payment.domain.entity.Reservation;

public interface PaymentRepository {

    Reservation save(Reservation reservation);

}
