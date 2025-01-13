package kr.hhplus.be.server.reservation.domain.repository;

import kr.hhplus.be.server.reservation.domain.entity.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation findBySeatId(Long seatId);

}
