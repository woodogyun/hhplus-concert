package kr.hhplus.be.server.payment.infra;

import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.payment.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements PaymentRepository {

    private final ReservationJPARepository reservationJPARepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJPARepository.save(reservation);
    }
}
