package kr.hhplus.be.server.reservation.domain.service;

import kr.hhplus.be.server.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.reservation.infra.ReservationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositoryImpl reservationRepository;

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
}

