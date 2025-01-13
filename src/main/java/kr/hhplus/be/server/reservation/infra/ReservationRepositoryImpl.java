package kr.hhplus.be.server.reservation.infra;

import kr.hhplus.be.server.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJPARepository reservationJPARepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJPARepository.save(reservation);
    }

    @Override
    public Reservation findBySeatId(Long seatId) {
        return reservationJPARepository.findBySeatId(seatId);
    }
}
