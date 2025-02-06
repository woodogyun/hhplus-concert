package kr.hhplus.be.server.api.reservation.domain.service;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.api.reservation.domain.entity.ReservationState;
import kr.hhplus.be.server.api.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    // 예약 데이터 생성
    public void setReserve(Long seatId, Long price, Long userId, LocalDateTime expiresAt) {
        Reservation reservation = Reservation.create(seatId, price, userId, expiresAt);
        reservationRepository.save(reservation);
    }

    public Long updateState(Long seatId) {
        Reservation reservation = reservationRepository.findBySeatId(seatId);
        reservation.complete();
        return reservationRepository.save(reservation).getId();
    }

    public List<Reservation> findExpiredReservations() {
        LocalDateTime currentTime = LocalDateTime.now();
        return reservationRepository.findExpiredReservations(ReservationState.IN_PROGRESS, currentTime);
    }

    public void expireReservation(Reservation reservation) {
        reservation.expire();
    }
}

