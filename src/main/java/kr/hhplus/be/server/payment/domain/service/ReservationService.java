package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.common.ReservationState;
import kr.hhplus.be.server.config.PolicyProperties;
import kr.hhplus.be.server.payment.domain.entity.Reservation;
import kr.hhplus.be.server.payment.infra.ReservationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PolicyProperties policyProperties;
    private final ReservationRepositoryImpl reservationRepository;
 
    // 예약 데이터 생성
    @Transactional
    public void setReserve(Long seatId, Long price, Long userId) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(policyProperties.getSeatExpiredMinutes());

        Reservation reservation = Reservation.builder()
                .userId(userId)
                .seatId(seatId)
                .price(price)
                .expireAt(expiresAt)
                .state(ReservationState.IN_PROGRESS)
                .build();
        reservationRepository.save(reservation);

    }

}
