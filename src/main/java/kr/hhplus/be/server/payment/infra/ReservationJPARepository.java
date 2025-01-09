package kr.hhplus.be.server.payment.infra;

import kr.hhplus.be.server.payment.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJPARepository extends JpaRepository<Reservation, Long> {

}
