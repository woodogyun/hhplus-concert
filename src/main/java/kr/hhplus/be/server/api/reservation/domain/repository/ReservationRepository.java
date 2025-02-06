package kr.hhplus.be.server.api.reservation.domain.repository;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.api.reservation.domain.entity.ReservationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findBySeatId(Long seatId);

    @Query("SELECT r FROM Reservation r WHERE r.state = :state AND r.expireAt < :currentTime")
    List<Reservation> findExpiredReservations(@Param("state") ReservationState state, @Param("currentTime") LocalDateTime currentTime);

}
