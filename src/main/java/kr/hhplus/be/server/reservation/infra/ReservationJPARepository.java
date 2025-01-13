package kr.hhplus.be.server.reservation.infra;

import kr.hhplus.be.server.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJPARepository extends JpaRepository<Reservation, Long> {

    Reservation findBySeatId(Long seatId);
}
